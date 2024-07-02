package com.example.blockbuster.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movieItem", indices = [Index(value = ["imdbId"], unique = true)])
data class MovieItem(
    @PrimaryKey
    val imdbId: String,
    val year: String,
    val title: String,
    val type: String,
    val poster: String,
    val isSaved: Boolean
)