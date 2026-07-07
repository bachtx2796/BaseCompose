package com.example.basecompose.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> concatenate(vararg flows: Flow<T>): Flow<T> = flowOf(*flows).flattenConcat()

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> merge(vararg flows: Flow<T>): Flow<T> = flowOf(*flows).flattenMerge()

/**
 * Avoids double-clicks on buttons by emitting the very first item and starting a timer.
 * All items that arrive before the timer expires will be discarded.
 *
 * @param periodMillis The period in milliseconds to wait before accepting the next item.
 * @return A new flow that throttles the emission of items.
 */
fun <T> Flow<T>.throttleFirst(periodMillis: Long = 1000L): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var lastTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                emit(value)
            }
        }
    }
}

/**
 * Ticker flow emits a signal after a specified period and an initial delay.
 *
 * @param period The time in milliseconds between each tick.
 * @param initialDelay The initial delay in milliseconds before the first tick.
 * @param repeatTime The number of times to tick, defaulting to [Int.MAX_VALUE].
 * @return A flow that emits a signal at the specified intervals.
 */
fun tickerFlow(
    period: Long,
    initialDelay: Long = 0L,
    repeatTime: Int = Int.MAX_VALUE
): Flow<Unit> = flow {
    delay(initialDelay)
    var time = repeatTime
    while (time > 0) {
        emit(Unit)
        delay(period)
        time -= 1
    }
}.buffer(-1)

/**
 * Fixed Ticker flow emits a signal after a specified period and an initial delay, excluding calculation time.
 *
 * @param period The time in milliseconds between each tick.
 * @param initialDelay The initial delay in milliseconds before the first tick.
 * @param repeatTime The number of times to tick, defaulting to [Int.MAX_VALUE].
 * @return A flow that emits a signal at the specified intervals.
 */
fun fixedTickerFlow(
    period: Long,
    initialDelay: Long = 0L,
    repeatTime: Int = Int.MAX_VALUE
): Flow<Unit> = flow {
    delay(initialDelay)
    var time = repeatTime
    while (time > 0) {
        emit(Unit)
        delay(period)
        time -= 1
    }
}.conflate()

/**
 * A ticker flow that emits a signal periodically (every [period] milliseconds) until [timeoutMillis] is reached.
 *
 * @param period The period in milliseconds between each tick.
 * @param timeoutMillis The total time in milliseconds after which no more ticks are emitted.
 * @param initialDelay The initial delay in milliseconds before the first tick.
 */
fun tickerFlowWithTimeout(
    period: Long,
    timeoutMillis: Long,
    initialDelay: Long = 0L
): Flow<Unit> = flow {
    val startTime = System.currentTimeMillis()
    delay(initialDelay)
    while (System.currentTimeMillis() - startTime < timeoutMillis) {
        emit(Unit)
        delay(period)
    }
}.buffer(-1)

/**
 * Creates a cold flow that produces a single value from the given suspend function.
 *
 * @param function The suspend function to produce a value from.
 * @return A flow that emits the result of the suspend function.
 */
fun <T> flowFromSuspend(function: suspend () -> T): Flow<T> = flow { emit(function()) }

/**
 * Emits null initially, followed by History(null,1), History(1,2), and so on.
 *
 * @return A flow that emits a history of values.
 */
//fun <T> Flow<T>.runningHistory(): Flow<History<T>?> = runningFold(
//    initial = null as (History<T>?),
//    operation = { accumulator, new -> History(accumulator?.current, new) }
//)

/**
 * Doesn't emit until the first value is available, then emits History(null,1), History(1,2), and so on.
 *
 * @return A flow that emits a non-null history of values.
 */
//fun <T> Flow<T>.runningHistoryAlternative(): Flow<History<T>> = runningHistory().filterNotNull()

/**
 * Resumes the coroutine safely, invoking an optional callback if the coroutine is no longer active.
 *
 * @param value The value to resume the coroutine with.
 * @param onExceptionCalled An optional callback to invoke if the coroutine is no longer active.
 */
fun <T> Continuation<T>.safeResume(
    value: T,
    onExceptionCalled: (() -> Unit)? = null
) {
    if (this is CancellableContinuation) {
        if (isActive) {
            resume(value)
        } else {
            onExceptionCalled?.invoke()
        }
    } else {
        throw Exception("Must use suspendCancellableCoroutine instead of suspendCoroutine")
    }
}

/**
 * Combines multiple flows and emits the transformed result as a new flow.
 * Ensures that it only emits when all flows have emitted.
 *
 * @param flows The flows to combine.
 * @param transform The transformation function to apply to the combined values.
 * @return A flow that emits the transformed result of the combined flows.
 */
inline fun <reified T, R> zipFlowAll(
    vararg flows: Flow<T>,
    crossinline transform: suspend (List<T>) -> R
): Flow<R> {
    require(flows.isNotEmpty()) { "At least one flow is required" }

    return flow {
        coroutineScope {
            val channels = flows.map { flow ->
                val channel = Channel<T>(Channel.UNLIMITED)
                launch {
                    flow.collect { value ->
                        channel.send(value)
                    }
                    channel.close()
                }
                channel
            }

            try {
                while (true) {
                    val values = channels.map {
                        it.receiveCatching().getOrNull()
                            ?: return@coroutineScope
                    }
                    emit(transform(values))
                }
            } finally {
                channels.forEach { it.cancel() }
            }
        }
    }
}

inline fun <reified T, R> zipFlowAll(
    flows: List<Flow<T>>,
    crossinline transform: suspend (List<T>) -> R
): Flow<R> {
    require(flows.isNotEmpty()) { "At least one flow is required" }

    return flow {
        coroutineScope {
            val channels = flows.map { flow ->
                val channel = Channel<T>(Channel.UNLIMITED)
                launch {
                    flow.collect { value ->
                        channel.send(value)
                    }
                    channel.close()
                }
                channel
            }

            try {
                while (true) {
                    val values = channels.map {
                        it.receiveCatching().getOrNull()
                            ?: return@coroutineScope
                    }
                    emit(transform(values))
                }
            } finally {
                channels.forEach { it.cancel() }
            }
        }
    }
}

inline fun <reified T, R> zipFlowNullable(
    flows: List<Flow<T>>,
    crossinline transform: suspend (List<T>) -> R
): Flow<R> {
    require(flows.isNotEmpty()) { "At least one flow is required" }

    return flow {
        coroutineScope {
            val channels = flows.map { flow ->
                val channel = Channel<T>(Channel.UNLIMITED)
                launch {
                    flow.collect { value ->
                        channel.send(value)
                    }
                    channel.close()
                }
                channel
            }

            try {
                while (true) {
                    val values = channels.mapNotNull {
                        it.receiveCatching().getOrNull()
                    }
                    emit(transform(values))
                }
            } finally {
                channels.forEach { it.cancel() }
            }
        }
    }
}

/**
 * Combines two flows and emits the transformed result as a new flow.
 * Ensures that it only emits when both flows have emitted.
 *
 * @param flow1 The first flow to combine.
 * @param flow2 The second flow to combine.
 * @param transform The transformation function to apply to the combined values.
 * @return A flow that emits the transformed result of the combined flows.
 */
inline fun <T1, T2, R> zipFlow(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    crossinline transform: suspend (T1, T2) -> R
): Flow<R> {
    return flow {
        coroutineScope {
            val channel1 = Channel<T1>(Channel.UNLIMITED)
            val channel2 = Channel<T2>(Channel.UNLIMITED)

            launch {
                flow1.collect { value -> channel1.send(value) }
                channel1.close()
            }
            launch {
                flow2.collect { value -> channel2.send(value) }
                channel2.close()
            }

            try {
                while (true) {
                    val value1 = channel1.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value2 = channel2.receiveCatching().getOrNull() ?: return@coroutineScope

                    emit(transform(value1, value2))
                }
            } finally {
                channel1.cancel()
                channel2.cancel()
            }
        }
    }
}

/**
 * Combines three flows and emits the transformed result as a new flow.
 * Ensures that it only emits when all three flows have emitted.
 *
 * @param flow1 The first flow to combine.
 * @param flow2 The second flow to combine.
 * @param flow3 The third flow to combine.
 * @param transform The transformation function to apply to the combined values.
 * @return A flow that emits the transformed result of the combined flows.
 */
inline fun <T1, T2, T3, R> zipFlow(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    crossinline transform: suspend (T1, T2, T3) -> R
): Flow<R> {
    return flow {
        coroutineScope {
            val channel1 = Channel<T1>(Channel.UNLIMITED)
            val channel2 = Channel<T2>(Channel.UNLIMITED)
            val channel3 = Channel<T3>(Channel.UNLIMITED)

            launch {
                flow1.collect { value -> channel1.send(value) }
                channel1.close()
            }
            launch {
                flow2.collect { value -> channel2.send(value) }
                channel2.close()
            }
            launch {
                flow3.collect { value -> channel3.send(value) }
                channel3.close()
            }

            try {
                while (true) {
                    val value1 = channel1.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value2 = channel2.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value3 = channel3.receiveCatching().getOrNull() ?: return@coroutineScope

                    emit(transform(value1, value2, value3))
                }
            } finally {
                channel1.cancel()
                channel2.cancel()
                channel3.cancel()
            }
        }
    }
}

/**
 * Combines four flows and emits the transformed result as a new flow.
 * Ensures that it only emits when all four flows have emitted.
 *
 * @param flow1 The first flow to combine.
 * @param flow2 The second flow to combine.
 * @param flow3 The third flow to combine.
 * @param flow4 The fourth flow to combine.
 * @param transform The transformation function to apply to the combined values.
 * @return A flow that emits the transformed result of the combined flows.
 */
inline fun <T1, T2, T3, T4, R> zipFlow(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    crossinline transform: suspend (T1, T2, T3, T4) -> R
): Flow<R> {
    return flow {
        coroutineScope {
            val channel1 = Channel<T1>(Channel.UNLIMITED)
            val channel2 = Channel<T2>(Channel.UNLIMITED)
            val channel3 = Channel<T3>(Channel.UNLIMITED)
            val channel4 = Channel<T4>(Channel.UNLIMITED)

            launch {
                flow1.collect { value -> channel1.send(value) }
                channel1.close()
            }
            launch {
                flow2.collect { value -> channel2.send(value) }
                channel2.close()
            }
            launch {
                flow3.collect { value -> channel3.send(value) }
                channel3.close()
            }
            launch {
                flow4.collect { value -> channel4.send(value) }
                channel4.close()
            }

            try {
                while (true) {
                    val value1 = channel1.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value2 = channel2.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value3 = channel3.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value4 = channel4.receiveCatching().getOrNull() ?: return@coroutineScope

                    emit(transform(value1, value2, value3, value4))
                }
            } finally {
                channel1.cancel()
                channel2.cancel()
                channel3.cancel()
                channel4.cancel()
            }
        }
    }
}

/**
 * Combines five flows and emits the transformed result as a new flow.
 * Ensures that it only emits when all five flows have emitted.
 *
 * @param flow1 The first flow to combine.
 * @param flow2 The second flow to combine.
 * @param flow3 The third flow to combine.
 * @param flow4 The fourth flow to combine.
 * @param flow5 The fifth flow to combine.
 * @param transform The transformation function to apply to the combined values.
 * @return A flow that emits the transformed result of the combined flows.
 */
inline fun <T1, T2, T3, T4, T5, R> zipFlow(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    crossinline transform: suspend (T1, T2, T3, T4, T5) -> R
): Flow<R> {
    return flow {
        coroutineScope {
            val channel1 = Channel<T1>(Channel.UNLIMITED)
            val channel2 = Channel<T2>(Channel.UNLIMITED)
            val channel3 = Channel<T3>(Channel.UNLIMITED)
            val channel4 = Channel<T4>(Channel.UNLIMITED)
            val channel5 = Channel<T5>(Channel.UNLIMITED)

            launch {
                flow1.collect { value -> channel1.send(value) }
                channel1.close()
            }
            launch {
                flow2.collect { value -> channel2.send(value) }
                channel2.close()
            }
            launch {
                flow3.collect { value -> channel3.send(value) }
                channel3.close()
            }
            launch {
                flow4.collect { value -> channel4.send(value) }
                channel4.close()
            }
            launch {
                flow5.collect { value -> channel5.send(value) }
                channel5.close()
            }

            try {
                while (true) {
                    val value1 = channel1.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value2 = channel2.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value3 = channel3.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value4 = channel4.receiveCatching().getOrNull() ?: return@coroutineScope
                    val value5 = channel5.receiveCatching().getOrNull() ?: return@coroutineScope

                    emit(transform(value1, value2, value3, value4, value5))
                }
            } finally {
                channel1.cancel()
                channel2.cancel()
                channel3.cancel()
                channel4.cancel()
                channel5.cancel()
            }
        }
    }
}

/**
 * Extension function to collect a flow lifecycle-aware.
 *
 * @param lifecycleOwner The lifecycle owner that controls the flow's lifecycle.
 * @param minActiveState The minimum state at which the flow should be collected. Default is [Lifecycle.State.STARTED].
 */
fun <T> Flow<T>.launchWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    this.flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
        .launchIn(lifecycleOwner.lifecycleScope)
}

// Define a private exception used solely to abort the flow.
private object AbortFlowException : CancellationException() {
    private fun readResolve(): Any = AbortFlowException
}

/**
 * Emits all items from the upstream flow until the [predicate] returns false,
 * but also emits the first item for which the predicate is false.
 */
fun <T> Flow<T>.takeWhileInclusive(predicate: suspend (T) -> Boolean): Flow<T> = flow {
    collect { value ->
        emit(value)
        if (!predicate(value)) {
            // Throwing a special exception to stop further collection.
            throw AbortFlowException
        }
    }
}.catch { e ->
    // If the exception is our special one, then we simply end the flow normally.
    if (e !is AbortFlowException) throw e
}

fun <T> Flow<T>.collectStarted(
    lifecycleOwner: LifecycleOwner,
    collector: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect { collector(it) }
        }
    }
}
