package com.example.blockbuster.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockbuster.data.local.repository.LocalRepository
import com.example.blockbuster.data.network.NetworkConnectivityManager
import com.example.blockbuster.data.remote.repository.RemoteRepository
import com.example.blockbuster.data.utils.getOrThrow
import com.example.blockbuster.data.utils.toMovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val networkConnectivityManager: NetworkConnectivityManager
) : ViewModel() {

    init {
        networkConnectivityManager.startListenNetworkState()
    }
    fun searchMovie(query: String) {
        viewModelScope.launch {
            networkConnectivityManager.isNetworkConnectedFlow.collectLatest {
                Log.d("jerrydev", "searchMovie: $it")
            }
            repository.searchMovie("red").collectLatest {
                localRepository.saveMovieItem(it.getOrThrow().apiMovieItems[0].toMovieItem())
                Log.d("jerrydev", "searchMovie: ${it.getOrThrow()}")

                localRepository.getAllMovieItems().collectLatest {
                    Log.d("jerrydev", "searchMovie: getAllMovieItems $it")
                }
            }
        }
    }
}