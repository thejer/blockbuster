package com.example.blockbuster.data.local.daos

import androidx.room.Query
import androidx.room.Upsert
import com.example.blockbuster.data.local.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsDao {

    @Upsert
    fun insertMovieItem(movieDetails: MovieDetails): Flow<Long>

    @Query("SELECT * FROM movieDetails WHERE imdbId = :imdbId")
    fun getMovieDetails(imdbId: String): Flow<MovieDetails>
}