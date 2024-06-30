package com.example.blockbuster.di

import com.example.blockbuster.BuildConfig
import com.example.blockbuster.BuildConfig.API_KEY
import com.example.blockbuster.BuildConfig.HOST_NAME
import com.example.blockbuster.data.remote.repository.MainRemoteRepository
import com.example.blockbuster.data.remote.repository.RemoteRepository
import com.example.blockbuster.data.remote.service.ApiService
import com.example.blockbuster.data.utils.NetworkExceptionInterceptor
import com.google.gson.Gson
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
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        paramInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(paramInterceptor)
            .addInterceptor(NetworkExceptionInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideParamInterceptor(): Interceptor =
        Interceptor { chain ->
            val url = chain.request().url.newBuilder()
                .addQueryParameter("apikey", API_KEY)
                .build()
            val requestBuilder = chain.request()
                .newBuilder()
                .url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(HOST_NAME)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): RemoteRepository =
        MainRemoteRepository(apiService)
}