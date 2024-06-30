package com.example.blockbuster.di

import android.app.Application
import androidx.room.Room
import com.example.blockbuster.data.local.BlockbusterDatabase
import com.example.blockbuster.data.local.daos.MovieDetailsDao
import com.example.blockbuster.data.local.daos.MovieItemDao
import com.example.blockbuster.data.local.repository.LocalRepository
import com.example.blockbuster.data.local.repository.MainLocalRepository
import com.example.blockbuster.data.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideBlockbusterDatabase(app: Application): BlockbusterDatabase =
        Room.databaseBuilder(app, BlockbusterDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMovieItemDao(blockbusterDatabase: BlockbusterDatabase): MovieItemDao =
        blockbusterDatabase.movieItemDao

    @Provides
    fun provideMovieDetailsDao(blockbusterDatabase: BlockbusterDatabase): MovieDetailsDao =
        blockbusterDatabase.movieDetailsDao

    @Provides
    @Singleton
    fun provideLocalRepository(
        movieItemDao: MovieItemDao,
        movieDetailsDao: MovieDetailsDao
    ): LocalRepository = MainLocalRepository(movieItemDao, movieDetailsDao)
}
