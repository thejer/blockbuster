package com.example.blockbuster.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockbuster.data.AppRepository
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.data.utils.failureOrThrow
import com.example.blockbuster.data.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(MovieSearchUiState())
    val uiState: LiveData<MovieSearchUiState>
        get() = _uiState

    private val queryStateFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            queryStateFlow
                .debounce(500)
                .mapLatest { query ->
                    if (query.length >= 2) {
                        searchMovie(query)
                    } else {
                        getAllLocalMovies()
                    }
                }.collect()
        }
    }

    private fun searchMovie(query: String) = viewModelScope.launch {
        repository.searchMovies(query).collectLatest { response ->
            if (response.isSuccess) {
                val movies = response.getOrThrow()
                _uiState.value = uiState.value?.copy(movies = movies, searchQuery = query)
            } else {
                _uiState.value = uiState.value?.copy(
                    movies = emptyList(),
                    errorMessage = response.failureOrThrow().error
                )
            }
        }
    }

    private fun getAllLocalMovies() = viewModelScope.launch {
        repository.getAllLocalMovies().collectLatest { response ->
            if (response.isSuccess) {
                val movies = response.getOrThrow()
                _uiState.value = uiState.value?.copy(movies = movies, searchQuery = "")
            } else {
                _uiState.value = uiState.value?.copy(
                    movies = emptyList(),
                    errorMessage = response.failureOrThrow().error
                )
            }
        }
    }

    fun saveMovie(imdbId: String) = viewModelScope.launch {
        repository.setMovieItemAsSaved(imdbId)
    }

    fun updateQuery(query: String) {
        _uiState.value = uiState.value?.copy(searchQuery = query)
        queryStateFlow.value = query
    }

    data class MovieSearchUiState(
        val movies: List<MovieItem> = emptyList(),
        val searchQuery: String = "",
        val errorMessage: String? = null
    )
}