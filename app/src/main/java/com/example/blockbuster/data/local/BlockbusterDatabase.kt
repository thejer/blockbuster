package com.example.blockbuster.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blockbuster.data.local.entities.MovieItem

@Database(
    entities = [MovieItem::class],
    version = 1,
    exportSchema = false
)
abstract class BlockbusterDatabase: RoomDatabase() {

}