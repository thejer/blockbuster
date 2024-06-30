package com.example.blockbuster.data.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectivityManager {

    val isNetworkConnectedFlow: StateFlow<Boolean>

    val isNetworkConnected: Boolean

    fun startListenNetworkState()

    fun stopListenNetworkState()
}