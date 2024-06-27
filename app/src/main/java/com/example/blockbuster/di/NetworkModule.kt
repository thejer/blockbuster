package com.example.blockbuster.di

import com.example.blockbuster.data.remote.repository.MainRemoteRepository
import com.example.blockbuster.data.remote.repository.RemoteRepository
import com.example.blockbuster.data.remote.service.ApiService
import com.example.blockbuster.data.remote.utils.NetworkExceptionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val BASE_URL = "http://www.omdbapi.com/"
        const val API_KEY = ""
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .apply {
                val paramInterceptor = Interceptor { chain ->
                    val url = chain.request().url.newBuilder()
                        .addQueryParameter("apikey", API_KEY)
                        .build()
                    val requestBuilder = chain.request()
                        .newBuilder()
                        .url(url)
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                addInterceptor(paramInterceptor)
            }
            .addInterceptor(NetworkExceptionInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    fun provideRepository(apiService: ApiService): RemoteRepository =
        MainRemoteRepository(apiService)
}