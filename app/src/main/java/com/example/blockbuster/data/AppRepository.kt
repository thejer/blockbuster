package com.example.blockbuster.data

import com.example.blockbuster.data.local.entities.MovieDetails
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.data.utils.DataResult
import com.example.blockbuster.data.utils.ErrorResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun searchMovies(query: String): Flow<DataResult<List<MovieItem>, ErrorResponse>>

    fun getMovieDetails(imdbId: String): Flow<DataResult<MovieDetails, ErrorResponse>>

    fun getAllLocalMovies(): Flow<DataResult<List<MovieItem>, ErrorResponse>>

    suspend fun saveMovie(movieItem: MovieItem)

    suspend fun saveMovieDetails(movieDetails: MovieDetails)

    fun getAllSavedMovies(): Flow<DataResult<List<MovieItem>, ErrorResponse>>

    fun searchSavedMovies(query: String): Flow<DataResult<List<MovieItem>, ErrorResponse>>

    suspend fun bulkSaveMovies(movies: List<MovieItem>)

    suspend fun setMovieItemAsSaved(imdbId: String)
}