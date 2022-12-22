package com.hourimeche.mvpmatchmovieapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@FlowPreview
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @get:Singleton
    @get:Provides
    val gson: Gson
        get() = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.interceptors().add(Interceptor { chain: Interceptor.Chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
                .method(originalRequest.method(), originalRequest.body())
            chain.proceed(builder.build())
        })
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

}
