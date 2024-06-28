package com.example.blockbuster.di

import android.app.Application
import androidx.room.Room
import com.example.blockbuster.data.local.BlockbusterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    private const val DATABASE_NAME = "blockbuster.db"

    @Provides
    @Singleton
    fun provideBlockbusterDatabase(app: Application): BlockbusterDatabase =
        Room.databaseBuilder(app, BlockbusterDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
}
