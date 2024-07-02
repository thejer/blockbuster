package com.example.blockbuster.data.remote.repository

import android.util.Log
import com.example.blockbuster.data.remote.model.ApiMovieDetails
import com.example.blockbuster.data.remote.model.ApiMovieSearchResult
import com.example.blockbuster.data.remote.service.ApiService
import com.example.blockbuster.data.utils.DataResult
import com.example.blockbuster.data.utils.ErrorResponse
import com.example.blockbuster.data.utils.GENERIC_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRemoteRepository @Inject constructor(private val apiService: ApiService) :
    RemoteRepository {

    override fun searchMovie(query: String): Flow<DataResult<ApiMovieSearchResult, ErrorResponse>> =
        flow {
            val response = apiService.searchMovies(query)
            Log.d("jerrydev", "searchMovie: ${response.body()}")
            if (response.isSuccessful || response.body()?.response == "True") {
                val data = response.body()
                emit(DataResult.success(data ?: throw Exception(GENERIC_ERROR_MESSAGE)))
            } else {
                Log.d("jerrydev", "searchMovie: $response")
                val errorBody = response.body()?.error ?: GENERIC_ERROR_MESSAGE
                emit(DataResult.failure(ErrorResponse(errorBody)))
            }
        }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(imdbId: String): Flow<DataResult<ApiMovieDetails, ErrorResponse>> {
        return flow {
            val response = apiService.getMovieById(imdbId, "full")
            if (response.isSuccessful || response.body()?.response == "True") {
                val data = response.body() ?: throw Exception(GENERIC_ERROR_MESSAGE)
                emit(DataResult.success(data))
            } else {
                Log.d("jerrydev", "getMovieDetails: $response")
                val errorBody = response.body()?.error ?: GENERIC_ERROR_MESSAGE
                emit(DataResult.failure(ErrorResponse(errorBody)))
            }
        }
    }
}