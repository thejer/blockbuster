package com.example.blockbuster.data.remote.repository

import com.example.blockbuster.data.remote.model.ApiMovieDetails
import com.example.blockbuster.data.remote.model.ApiMovieSearchResult
import com.example.blockbuster.data.utils.DataResult
import com.example.blockbuster.data.utils.ErrorResponse
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun searchMovie(query: String): Flow<DataResult<ApiMovieSearchResult, ErrorResponse>>

    fun getMovieDetails(imdbId: String): Flow<DataResult<ApiMovieDetails, ErrorResponse>>
}