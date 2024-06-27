package com.example.blockbuster.data.remote.utils


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("Error")
    val error: String,
    @SerializedName("Response")
    val response: String
)