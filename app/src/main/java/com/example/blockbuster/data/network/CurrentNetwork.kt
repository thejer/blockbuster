package com.example.blockbuster.data.network

import android.net.NetworkCapabilities

data class CurrentNetwork(
    val isListening: Boolean,
    val networkCapabilities: NetworkCapabilities?,
    val isAvailable: Boolean,
)