package com.example.blockbuster.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movieDetails", indices = [Index(value = ["imdbId"], unique = true)])
data class MovieDetails(
    @PrimaryKey
    val imdbId: String,
    val actors: String,
    val awards: String,
    val boxOffice: String,
    val country: String,
    val director: String,
    val dvd: String,
    val genre: String,
    val imdbRating: String,
    val imdbVotes: String,
    val language: String,
    val metaScore: String,
    val plot: String,
    val poster: String,
    val production: String,
    val rated: String,
    val ratings: List<Rating>,
    val released: String,
    val response: String,
    val runtime: String,
    val title: String,
    val type: String,
    val website: String,
    val writer: String,
    val year: String
)