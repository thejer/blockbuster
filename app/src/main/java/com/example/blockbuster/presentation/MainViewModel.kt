package com.example.blockbuster.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockbuster.data.network.NetworkConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkConnectivityManager: NetworkConnectivityManager
): ViewModel() {

    private val _uiState = MutableLiveData(MainUiState(isOnline = true, showBanner = false))
    val uiState: LiveData<MainUiState>
        get() = _uiState

    private fun listenForNetworkState() = viewModelScope.launch {
        networkConnectivityManager.isNetworkConnectedFlow.collectLatest {
            _uiState.value = uiState.value?.copy(isOnline = it, showBanner = true)
            if (it) {
                delay(2_000)
                _uiState.value = uiState.value?.copy(showBanner = false)
            }
        }
    }

    fun registerNetworkListener() {
        networkConnectivityManager.startListenNetworkState()
        listenForNetworkState()
    }

    fun unregisterNetworkListener() {
        networkConnectivityManager.stopListenNetworkState()
    }

    data class MainUiState(
        val isOnline: Boolean,
        val showBanner: Boolean
    )
}