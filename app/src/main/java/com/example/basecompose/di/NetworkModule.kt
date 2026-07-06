package com.example.basecompose.di

import com.example.basecompose.BuildConfig
import com.example.basecompose.data.remote.base.interceptor.DefaultHeadersInterceptor
import com.example.basecompose.di.qualifier.BaseUrl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val NETWORK_TIMEOUT_SECONDS = 30L

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpCallFactory(
        defaultHeadersInterceptor: DefaultHeadersInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): Call.Factory {
        return OkHttpClient.Builder()
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(defaultHeadersInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gson: Gson,
        okHttpCallFactory: Lazy<Call.Factory>
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .callFactory { request -> okHttpCallFactory.get().newCall(request) }
            .addConverterFactory(GsonConverterFactory.create(gson))
    }
}
