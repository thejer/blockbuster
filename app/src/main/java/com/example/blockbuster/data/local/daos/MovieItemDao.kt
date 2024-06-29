package com.example.blockbuster.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.blockbuster.data.local.entities.MovieItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieItemDao {

    @Upsert
    suspend fun saveMovie(movieItem: MovieItem)

    @Query("SELECT * FROM movieItem")
    fun getAllMovies(): Flow<List<MovieItem>>

    @Query("SELECT * FROM movieItem WHERE imdbId = :imdbId")
    fun getMovieById(imdbId: String): Flow<List<MovieItem>>

    @Query("SELECT * FROM movieItem WHERE title LIKE :query")
    fun searchMovieByTitle(query: String): Flow<List<MovieItem>>

    @Delete
    suspend fun deleteMovie(movieItem: MovieItem)
}