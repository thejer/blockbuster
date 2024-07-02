package com.example.blockbuster.data

import com.example.blockbuster.data.local.entities.MovieDetails
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.data.utils.DataResult
import com.example.blockbuster.data.utils.ErrorResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun searchMovies(query: String): Flow<DataResult<List<MovieItem>, ErrorResponse>>

    suspend fun getMovieDetails(imdbId: String): Flow<DataResult<MovieDetails, ErrorResponse>>

    suspend fun saveMovie(movieItem: MovieItem)
}