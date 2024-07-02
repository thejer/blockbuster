package com.example.blockbuster.data.remote.repository

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
            try {
                val response = apiService.searchMovies(query)
                if (response.isSuccessful && response.body()?.response == "True") {
                    val data = response.body()
                    emit(DataResult.success(data ?: throw Exception(GENERIC_ERROR_MESSAGE)))
                } else {
                    val errorBody = response.body()?.error ?: GENERIC_ERROR_MESSAGE
                    emit(DataResult.failure(ErrorResponse(errorBody)))
                }
            } catch (exception: Exception) {
                emit(DataResult.failure(ErrorResponse(exception.message ?: "An error occurred")))
            }
        }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(imdbId: String): Flow<DataResult<ApiMovieDetails, ErrorResponse>> {
        return flow {
            try {
                val response = apiService.getMovieById(imdbId, "full")
                if (response.isSuccessful && response.body()?.response == "True") {
                    val data = response.body() ?: throw Exception(GENERIC_ERROR_MESSAGE)
                    emit(DataResult.success(data))
                } else {
                    val errorBody = response.body()?.error ?: GENERIC_ERROR_MESSAGE
                    emit(DataResult.failure(ErrorResponse(errorBody)))
                }
            } catch (exception: Exception) {
                emit(DataResult.failure(ErrorResponse(exception.message ?: "An error occurred")))
            }
        }.flowOn(Dispatchers.IO)
    }
}