package com.example.blockbuster.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockbuster.data.remote.repository.RemoteRepository
import com.example.blockbuster.data.utils.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {

    fun searchMovie(query: String) {
        viewModelScope.launch {
            repository.searchMovie("").collectLatest {
                Log.d("jerrydev", "searchMovie: ${it.getOrThrow()}")
            }
        }
    }
}