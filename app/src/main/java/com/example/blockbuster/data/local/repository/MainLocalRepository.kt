package com.example.blockbuster.data.local.repository

import com.example.blockbuster.data.local.daos.MovieDetailsDao
import com.example.blockbuster.data.local.daos.MovieItemDao
import com.example.blockbuster.data.local.entities.MovieDetails
import com.example.blockbuster.data.local.entities.MovieItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainLocalRepository @Inject constructor(
    private val movieItemDao: MovieItemDao,
    private val movieDetailsDao: MovieDetailsDao
) : LocalRepository {

    override suspend fun getMovieDetails(imdbId: String) = flow { emit(movieDetailsDao.getMovieDetails(imdbId))}

    override suspend fun saveMovieDetails(movieDetails: MovieDetails) = movieDetailsDao.insertMovieDetails(movieDetails)

    override suspend fun saveMovieItem(movieItem: MovieItem) = movieItemDao.saveMovie(movieItem)

    override suspend fun getAllMovieItems(): Flow<List<MovieItem>> = flow { emit(movieItemDao.getAllMovies()) }

    override suspend fun searchMovieItemById(imdbId: String): Flow<List<MovieItem>> = flow { emit(movieItemDao.getMovieById(imdbId))}

    override suspend fun searchMovieItemByTitle(title: String): Flow<List<MovieItem>> = flow { emit(movieItemDao.searchMovieByTitle(title))}

    override suspend fun getAllSavedMovies(): Flow<List<MovieItem>> = flow { emit(movieItemDao.getAllSavedMovies())}

    override suspend fun searchSavedMovies(query: String): Flow<List<MovieItem>> = flow { emit(movieItemDao.searchSavedMovies(query))}

    override suspend fun bulkSaveMovies(movies: List<MovieItem>) = movieItemDao.bulkSaveMovie(movies)

    override suspend fun setMovieItemAsSaved(imdbId: String) = movieItemDao.setMovieItemAsSaved(imdbId)
}