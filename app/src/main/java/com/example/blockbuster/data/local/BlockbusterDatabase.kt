package com.example.blockbuster.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blockbuster.data.local.daos.MovieDetailsDao
import com.example.blockbuster.data.local.daos.MovieItemDao
import com.example.blockbuster.data.local.entities.MovieDetails
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.data.local.entities.Rating

@Database(
    entities = [MovieItem::class, MovieDetails::class, Rating::class],
    version = 1,
    exportSchema = false
)
abstract class BlockbusterDatabase : RoomDatabase() {

    abstract val movieItemDao: MovieItemDao

    abstract val movieDetailsDao: MovieDetailsDao
}