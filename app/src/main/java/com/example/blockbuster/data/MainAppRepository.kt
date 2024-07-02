package com.example.blockbuster.data

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

class MainAppRepository @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : AppRepository {

    override suspend fun searchMovies(query: String): Flow<DataResult<List<MovieItem>, ErrorResponse>> = channelFlow {
        localRepository.searchMovieItemByTitle(query).collectLatest { movieItems ->
            if (movieItems.isEmpty()) {
                remoteRepository.searchMovie(query).collectLatest { remoteResult ->
                    if (remoteResult.isSuccess) {
                        val movies = remoteResult.getOrThrow().apiMovieItems?.map { it.toMovieItem() } ?: emptyList()
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

    override suspend fun getMovieDetails(imdbId: String): Flow<DataResult<MovieDetails, ErrorResponse>> = channelFlow {
        localRepository.getMovieDetails(imdbId).collectLatest { movieDetails ->
            if (movieDetails.isEmpty()) {
                remoteRepository.getMovieDetails(imdbId).collectLatest { remoteResult ->
                    if (remoteResult.isSuccess) {
                        send(DataResult.success(remoteResult.getOrThrow().toMovieDetails()))
                    } else {
                        send(DataResult.failure(remoteResult.failureOrThrow()))
                    }
                }
            } else {
                send(DataResult.success(movieDetails.first()))
            }
        }
    }

    override suspend fun saveMovie(movieItem: MovieItem) {
        TODO("Not yet implemented")
    }
}