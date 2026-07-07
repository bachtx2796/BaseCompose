package com.example.basecompose.ext

fun String?.orDefault(default: String): String = if (this.isNullOrEmpty()) default else this

fun List<String>.toHashtagText(): String {
    return filter { it.isNotBlank() }
        .joinToString(" ") { "$it" }
}

fun Long?.toMinuteText(): String {
    val millis = this ?: 0
    val minutes = millis / 1000 / 60
    return "$minutes phút"
}

fun String?.toSmartHashtag(): String {
    if (this.isNullOrBlank()) return ""
    return try {
        val formatted = this.trim()
            .split(Regex("[\\s_-]+"))
            .filter { it.isNotEmpty() }
            .joinToString("") { word ->
                word.lowercase().replaceFirstChar { it.uppercase() }
            }

        if (formatted.isEmpty()) "" else "#$formatted"
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun Long.toTimeFormat(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

const val MIN_DAILY_MINUTES = 1

fun String.toDailyMinutesTarget(): Int {
    return filter { it.isDigit() }.toIntOrNull()?.coerceAtLeast(MIN_DAILY_MINUTES)
        ?: MIN_DAILY_MINUTES
}
