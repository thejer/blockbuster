package com.example.blockbuster.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.blockbuster.data.local.entities.MovieDetails

@Dao
interface MovieDetailsDao {

    @Upsert
    suspend fun insertMovieDetails(movieDetails: MovieDetails)

    @Query("SELECT * FROM movieDetails WHERE imdbId = :imdbId")
    fun getMovieDetails(imdbId: String): List<MovieDetails>
}