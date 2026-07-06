package com.example.basecompose.data.remote.base

import com.example.basecompose.di.qualifier.BaseUrl
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Retrofit

@Singleton
class RetrofitServiceFactory @Inject constructor(
    private val retrofitBuilder: Retrofit.Builder,
    @BaseUrl private val defaultBaseUrl: String
) {
    fun <T> create(
        serviceClass: Class<T>,
        baseUrl: String = defaultBaseUrl
    ): T {
        return retrofitBuilder
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}
