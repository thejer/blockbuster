package com.example.blockbuster.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "rating", indices = [Index(value = ["imdbId"], unique = true)])
data class Rating(
    @PrimaryKey
    val imdbId: String,
    val source: String,
    val value: String
) {
    override fun toString(): String {
        return "$source $value"
    }
}