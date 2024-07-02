package com.example.blockbuster.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockbuster.data.AppRepository
import com.example.blockbuster.data.local.entities.MovieDetails
import com.example.blockbuster.data.utils.failureOrThrow
import com.example.blockbuster.data.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    private val _uiState = MutableLiveData(MovieDetailsUiState())
    val uiState: LiveData<MovieDetailsUiState>
        get() = _uiState


    fun getMovieDetails(imdbId: String) = viewModelScope.launch {
        repository.getMovieDetails(imdbId).collectLatest { response ->
            if (response.isSuccess) {
                val movieDetails = response.getOrThrow()
                _uiState.value = uiState.value?.copy(movieDetails = movieDetails)
            } else {
                _uiState.value = uiState.value?.copy(
                    errorMessage = response.failureOrThrow().error
                )
            }
        }
    }

    fun toggleViewMoreDetails() {
        val movieDetailsUiState = uiState.value
        _uiState.value = movieDetailsUiState?.copy(isViewMoreDetails = !movieDetailsUiState.isViewMoreDetails)
    }

    data class MovieDetailsUiState(
        val movieDetails: MovieDetails? = null,
        val isViewMoreDetails: Boolean = false,
        val errorMessage: String? = null
    )
}
