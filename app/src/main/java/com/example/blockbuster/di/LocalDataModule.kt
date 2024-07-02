package com.example.blockbuster.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
            .addMigrations(object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE movieItem  ADD COLUMN isSaved INTEGER DEFAULT 0 NOT NULL")
                }
            }).build()

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
