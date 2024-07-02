package com.example.blockbuster.data.local.repository

import com.example.blockbuster.data.local.daos.MovieDetailsDao
import com.example.blockbuster.data.local.daos.MovieItemDao
import com.example.blockbuster.data.local.entities.MovieItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainLocalRepository @Inject constructor(
    private val movieItemDao: MovieItemDao,
    private val movieDetailsDao: MovieDetailsDao
) : LocalRepository {

    override suspend fun getMovieDetails(imdbId: String) = movieDetailsDao.getMovieDetails(imdbId)

    override suspend fun saveMovieItem(movieItem: MovieItem) = movieItemDao.saveMovie(movieItem)

    override fun getAllMovieItems(): Flow<List<MovieItem>> =
        movieItemDao.getAllMovies()

    override fun searchMovieItemById(imdbId: String): Flow<List<MovieItem>> =
        movieItemDao.getMovieById(imdbId)

    override fun searchMovieItemByTitle(title: String): Flow<List<MovieItem>> =
        movieItemDao.searchMovieByTitle(title)

    override suspend fun deleteMovieItem(movieItem: MovieItem) = movieItemDao.deleteMovie(movieItem)
}