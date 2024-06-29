package com.example.blockbuster.data.local.repository

import com.example.blockbuster.data.local.entities.MovieItem
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun saveMovieItem(movieItem: MovieItem)

    fun getAllMovieItems(): Flow<List<MovieItem>>

    fun searchMovieItemById(imdbId: String): Flow<List<MovieItem>>

    fun searchMovieItemByTitle(title: String): Flow<List<MovieItem>>

    suspend fun deleteMovieItem(movieItem: MovieItem)
}