package com.example.blockbuster.data.remote.model

import com.google.gson.annotations.SerializedName

data class ApiMovieItem(
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Year")
    val year: String
)

data class ApiMovieSearchResult(
    @SerializedName("Response")
    val response: String,
    @SerializedName("ApiMovieItem")
    val apiMovieItems: List<ApiMovieItem>,
    @SerializedName("totalResults")
    val totalResults: String
)