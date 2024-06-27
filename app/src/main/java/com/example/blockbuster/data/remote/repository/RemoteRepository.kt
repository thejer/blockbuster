package com.example.blockbuster.data.remote.repository

import com.example.blockbuster.data.remote.model.MovieSearchResultResponse
import com.example.blockbuster.data.remote.utils.ApiResult
import com.example.blockbuster.data.remote.utils.ErrorResponse
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun searchMovie(query: String): Flow<ApiResult<MovieSearchResultResponse, ErrorResponse>>
}