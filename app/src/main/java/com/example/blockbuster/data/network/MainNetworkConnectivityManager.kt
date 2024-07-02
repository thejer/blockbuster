package com.example.blockbuster.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.blockbuster.data.utils.isConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Singleton

@Singleton
class MainNetworkConnectivityManager(
    @ApplicationContext context: Context,
    private val coroutineScope: CoroutineScope
) : NetworkConnectivityManager {

    private val connectivityManager =
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .build()

    private val _currentNetwork = MutableStateFlow(createDefaultCurrentNetwork())

    private val networkCallback = NetworkCallback()

    override val isNetworkConnectedFlow: StateFlow<Boolean>
        get() = _currentNetwork.map { it.isConnected() }
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = _currentNetwork.value.isConnected()
            )

    override val isNetworkConnected: Boolean
        get() = isNetworkConnectedFlow.value

    override fun startListenNetworkState() {
        if (_currentNetwork.value.isListening) return
        _currentNetwork.update {
            createDefaultCurrentNetwork()
                .copy(isListening = true)
        }

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun stopListenNetworkState() {
        if (!_currentNetwork.value.isListening) return
        _currentNetwork.update {
            createDefaultCurrentNetwork()
                .copy(isListening = false)
        }
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun createDefaultCurrentNetwork(): CurrentNetwork {
        return CurrentNetwork(
            isListening = false,
            networkCapabilities = null,
            isAvailable = false,
        )
    }

    private inner class NetworkCallback : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            _currentNetwork.update {
                it.copy(isAvailable = true)
            }
        }

        override fun onLost(network: Network) {
            _currentNetwork.update {
                it.copy(
                    isAvailable = false,
                    networkCapabilities = null
                )
            }
        }

        override fun onUnavailable() {
            _currentNetwork.update {
                it.copy(
                    isAvailable = false,
                    networkCapabilities = null
                )
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            _currentNetwork.update {
                it.copy(networkCapabilities = networkCapabilities)
            }
        }
    }

}