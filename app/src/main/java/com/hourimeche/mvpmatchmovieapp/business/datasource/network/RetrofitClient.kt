package com.hourimeche.mvpmatchmovieapp.business.datasource.network


import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    init {
        httpClient.interceptors().add(Interceptor { chain: Interceptor.Chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
                .method(originalRequest.method(), originalRequest.body())
            chain.proceed(builder.build())
        })
        val retrofit: Retrofit =
            Retrofit.Builder().client(httpClient.build()).baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(MoviesService::class.java)
    }

    val apiService: MoviesService?
        get() = service

    companion object {
        private val httpClient = OkHttpClient.Builder()
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }
            private set
        private var service: MoviesService? = null
    }
}