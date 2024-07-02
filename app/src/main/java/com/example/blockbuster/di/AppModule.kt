package com.example.blockbuster.di

import android.content.Context
import com.example.blockbuster.data.AppRepository
import com.example.blockbuster.data.MainAppRepository
import com.example.blockbuster.data.local.repository.LocalRepository
import com.example.blockbuster.data.network.MainNetworkConnectivityManager
import com.example.blockbuster.data.network.NetworkConnectivityManager
import com.example.blockbuster.data.remote.repository.RemoteRepository
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

    @Provides
    @Singleton
    fun provideAppRepository(localRepository: LocalRepository, remoteRepository: RemoteRepository): AppRepository =
        MainAppRepository(localRepository, remoteRepository)
}