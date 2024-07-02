package com.example.blockbuster.data.local.repository

import com.example.blockbuster.data.local.entities.MovieDetails
import com.example.blockbuster.data.local.entities.MovieItem
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun saveMovieItem(movieItem: MovieItem)

    suspend fun getAllMovieItems(): Flow<List<MovieItem>>

    suspend fun searchMovieItemById(imdbId: String): Flow<List<MovieItem>>

    suspend fun searchMovieItemByTitle(title: String): Flow<List<MovieItem>>

    suspend fun getMovieDetails(imdbId: String): Flow<List<MovieDetails>>

    suspend fun saveMovieDetails(movieDetails: MovieDetails)

    suspend fun getAllSavedMovies(): Flow<List<MovieItem>>

    suspend fun searchSavedMovies(query: String): Flow<List<MovieItem>>

    suspend fun bulkSaveMovies(movies: List<MovieItem>)

    suspend fun setMovieItemAsSaved(imdbId: String)
}