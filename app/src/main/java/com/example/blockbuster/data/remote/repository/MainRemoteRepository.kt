package com.example.blockbuster.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.blockbuster.data.remote.model.MovieSearchResultResponse
import com.example.blockbuster.data.remote.service.ApiService
import com.example.blockbuster.data.remote.utils.ApiResult
import com.example.blockbuster.data.remote.utils.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class MainRemoteRepository @Inject constructor(private val apiService: ApiService): RemoteRepository {
    override fun searchMovie(query: String): Flow<ApiResult<MovieSearchResultResponse, ErrorResponse>> {
        return flow {
            val response = apiService.searchMovies(query, 1)
            if (response.isSuccessful) {
                val value = response.body()
                emit(ApiResult.success(value ?: throw Exception("")))
            } else {
                Log.d("jerrydev", "searchMovie: $response")
                emit(ApiResult.failure(ErrorResponse("", "")))
            }
        }.flowOn(Dispatchers.IO)
    }
}