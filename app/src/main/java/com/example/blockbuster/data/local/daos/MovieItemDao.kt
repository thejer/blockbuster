package com.example.blockbuster.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.blockbuster.data.local.entities.MovieItem

@Dao
interface MovieItemDao {

    @Upsert
    suspend fun saveMovie(movieItem: MovieItem)

    @Upsert
    suspend fun bulkSaveMovie(movies: List<MovieItem>)

    @Query("SELECT * FROM movieItem")
    suspend fun getAllMovies(): List<MovieItem>

    @Query("SELECT * FROM movieItem WHERE imdbId = :imdbId")
    suspend fun getMovieById(imdbId: String): List<MovieItem>

    @Query("SELECT * FROM movieItem WHERE title LIKE :query")
    suspend fun searchMovieByTitle(query: String): List<MovieItem>

    @Query("SELECT * FROM movieItem WHERE isSaved = 1")
    suspend fun getAllSavedMovies(): List<MovieItem>

    @Query("SELECT * FROM movieItem WHERE isSaved = 1 AND title LIKE :query")
    suspend fun searchSavedMovies(query: String): List<MovieItem>

    @Query("UPDATE movieItem SET isSaved = 1 WHERE imdbId = :imdbId")
    suspend fun setMovieItemAsSaved(imdbId: String)

    @Delete
    suspend fun deleteMovie(movieItem: MovieItem)
}