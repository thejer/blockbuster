package com.example.blockbuster.data.remote.repository

import android.util.Log
import com.example.blockbuster.data.remote.model.ApiMovieDetails
import com.example.blockbuster.data.remote.model.ApiMovieSearchResult
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

    override fun searchMovie(query: String): Flow<ApiResult<ApiMovieSearchResult, ErrorResponse>> =
        flow {
            val response = apiService.searchMovies(query)
            if (response.isSuccessful) {
                val data = response.body()
                emit(ApiResult.success(data ?: throw Exception("An error has occurred"))) // TODO: show better exception
            } else {
                Log.d("jerrydev", "searchMovie: $response")
                emit(ApiResult.failure(ErrorResponse("", "")))
            }
        }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(imdbId: String): Flow<ApiResult<ApiMovieDetails, ErrorResponse>> {
        return flow {
            val response = apiService.getMovieById(imdbId, "full")
            if (response.isSuccessful) {
                val data = response.body()
                emit(ApiResult.success(data ?: throw Exception("An error has occurred"))) // TODO: show better exception
            } else {
                Log.d("jerrydev", "getMovieDetails: $response")
                emit(ApiResult.failure(ErrorResponse("", "")))
            }
        }
    }
}