package com.example.blockbuster.data.remote.model


import com.google.gson.annotations.SerializedName

data class MovieSearchResultResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: List<Search>,
    @SerializedName("totalResults")
    val totalResults: String
)