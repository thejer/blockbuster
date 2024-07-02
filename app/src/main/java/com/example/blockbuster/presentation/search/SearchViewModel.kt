package com.example.blockbuster.presentation.search

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
class SearchViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData<MovieSearchUiState>()
    val uiState: LiveData<MovieSearchUiState>
        get() = _uiState

    init {
        _uiState.value = MovieSearchUiState(movies = emptyList())
    }

    fun searchMovie(query: String) = viewModelScope.launch {
            repository.searchMovies(query).collectLatest { response ->
                if (response.isSuccess) {
                    val movies = response.getOrThrow()
                    _uiState.value = uiState.value?.copy(movies = movies)
                } else {
                    // show error
                }
            }
        }


    data class MovieSearchUiState(
        val movies: List<MovieItem>,
    )
}