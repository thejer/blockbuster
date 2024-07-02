package com.example.blockbuster.presentation.watchlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockbuster.data.AppRepository
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.data.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel@Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData(WatchListUiState(movies = emptyList(), searchQuery = ""))
    val uiState: LiveData<WatchListUiState>
        get() = _uiState

    init {
        getAllSavedMovies()
    }

    fun getAllSavedMovies() = viewModelScope.launch {
        Log.d("jerrydev", "getAllSavedMovies: ")
        repository.getAllSavedMovies().collectLatest { response ->
            if (response.isSuccess) {
                val movies = response.getOrThrow()
                Log.d("jerrydev", "getAllSavedMovies: $movies")
                _uiState.value = uiState.value?.copy(movies = movies, searchQuery = "")
            } else {
                _uiState.value = uiState.value?.copy(movies = emptyList())
                // show error
            }
        }
    }

    fun searchMovie(query: String) = viewModelScope.launch {
        repository.searchSavedMovies(query).collectLatest { response ->
            if (response.isSuccess) {
                val movies = response.getOrThrow()
                _uiState.value = uiState.value?.copy(movies = movies, searchQuery = query)
            } else {
                _uiState.value = uiState.value?.copy(movies = emptyList())
                // show error
            }
        }
    }

    fun updateQuery(query: String) {
        _uiState.value = uiState.value?.copy(searchQuery = query)
    }

    data class WatchListUiState(
        val movies: List<MovieItem>,
        val searchQuery: String
    )
}