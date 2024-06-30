package com.example.blockbuster.di

import android.content.Context
import com.example.blockbuster.data.network.MainNetworkConnectivityManager
import com.example.blockbuster.data.network.NetworkConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [LocalDataModule::class, NetworkModule::class])
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @Provides
    @Singleton
    fun provideNetworkConnectivityManager(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
    ): NetworkConnectivityManager = MainNetworkConnectivityManager(context, coroutineScope)
}