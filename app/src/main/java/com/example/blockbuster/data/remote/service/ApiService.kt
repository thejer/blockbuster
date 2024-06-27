package com.example.blockbuster.data.remote.service

import com.example.blockbuster.data.remote.model.MovieSearchResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun searchMovies(
        @Query("s") searchQuery: String,
        @Query("page") page: Int
    ): Response<MovieSearchResultResponse>
}