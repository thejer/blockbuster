package com.example.blockbuster.data.remote.service

import com.example.blockbuster.data.remote.model.ApiMovieDetails
import com.example.blockbuster.data.remote.model.ApiMovieSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun searchMovies(
        @Query("s") searchQuery: String,
    ): Response<ApiMovieSearchResult>

    suspend fun getMovieById(
        @Query("i") imdbIf: String,
        @Query("plot") plot: String
    ): Response<ApiMovieDetails>
}