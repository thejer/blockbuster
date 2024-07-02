package com.example.blockbuster.data

import android.util.Log
import com.example.blockbuster.data.local.entities.MovieDetails
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.data.local.repository.LocalRepository
import com.example.blockbuster.data.remote.repository.RemoteRepository
import com.example.blockbuster.data.utils.DataResult
import com.example.blockbuster.data.utils.ErrorResponse
import com.example.blockbuster.data.utils.failureOrThrow
import com.example.blockbuster.data.utils.getOrThrow
import com.example.blockbuster.data.utils.toMovieDetails
import com.example.blockbuster.data.utils.toMovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainAppRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : AppRepository {

    override fun searchMovies(query: String): Flow<DataResult<List<MovieItem>, ErrorResponse>> =
        channelFlow {
            localRepository.searchMovieItemByTitle(query).collectLatest { movieItems ->
                if (movieItems.isEmpty()) {
                    remoteRepository.searchMovie(query).collectLatest { remoteResult ->
                        if (remoteResult.isSuccess) {
                            val movies =
                                remoteResult.getOrThrow().apiMovieItems?.map { it.toMovieItem() }
                                    ?: emptyList()
                            bulkSaveMovies(movies)
                            send(DataResult.success(movies))
                        } else {
                            send(DataResult.failure(remoteResult.failureOrThrow()))
                        }
                    }
                } else {
                    send(DataResult.success(movieItems))
                }
            }
        }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(imdbId: String): Flow<DataResult<MovieDetails, ErrorResponse>> =
        channelFlow {
            localRepository.getMovieDetails(imdbId).collectLatest { movieDetails ->
                if (movieDetails.isEmpty()) {
                    remoteRepository.getMovieDetails(imdbId).collectLatest { remoteResult ->
                        if (remoteResult.isSuccess) {
                            val details = remoteResult.getOrThrow().toMovieDetails()
                            saveMovieDetails(details)
                            send(DataResult.success(details))
                        } else {
                            send(DataResult.failure(remoteResult.failureOrThrow()))
                        }
                    }
                } else {
                    send(DataResult.success(movieDetails.first()))
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun setMovieItemAsSaved(imdbId: String) {
        Log.d("jerrydev", "setMovieItemAsSaved: $imdbId")
        localRepository.setMovieItemAsSaved(imdbId)
    }

    override fun getAllLocalMovies(): Flow<DataResult<List<MovieItem>, ErrorResponse>> =
        channelFlow {
            localRepository.getAllMovieItems()
                .collectLatest { moviesResult -> send(DataResult.success(moviesResult)) }
        }.flowOn(Dispatchers.IO)

    override suspend fun saveMovie(movieItem: MovieItem) = localRepository.saveMovieItem(movieItem)

    override suspend fun bulkSaveMovies(movies: List<MovieItem>) = localRepository.bulkSaveMovies(movies)

    override suspend fun saveMovieDetails(movieDetails: MovieDetails) =
        localRepository.saveMovieDetails(movieDetails)

    override fun getAllSavedMovies(): Flow<DataResult<List<MovieItem>, ErrorResponse>> =
        channelFlow {
            localRepository.getAllSavedMovies().collectLatest { moviesResult ->
                send(DataResult.success(moviesResult))
            }
        }.flowOn(Dispatchers.IO)

    override fun searchSavedMovies(query: String): Flow<DataResult<List<MovieItem>, ErrorResponse>> =
        channelFlow {
            localRepository.searchSavedMovies(query).collectLatest { moviesResult ->
                send(DataResult.success(moviesResult))
            }
        }.flowOn(Dispatchers.IO)
}