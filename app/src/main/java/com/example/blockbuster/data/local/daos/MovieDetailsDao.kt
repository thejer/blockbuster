package com.example.blockbuster.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.blockbuster.data.local.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {

    @Upsert
    suspend fun insertMovieItem(movieDetails: MovieDetails)

    @Query("SELECT * FROM movieDetails WHERE imdbId = :imdbId")
    fun getMovieDetails(imdbId: String): Flow<List<MovieDetails>>
}