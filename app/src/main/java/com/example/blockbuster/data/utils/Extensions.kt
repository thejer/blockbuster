package com.example.blockbuster.data.utils

import android.net.NetworkCapabilities
import com.example.blockbuster.data.local.entities.MovieDetails
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.data.local.entities.Rating
import com.example.blockbuster.data.network.CurrentNetwork
import com.example.blockbuster.data.remote.model.ApiMovieDetails
import com.example.blockbuster.data.remote.model.ApiMovieItem
import com.example.blockbuster.data.remote.model.ApiRating

private fun NetworkCapabilities?.isNetworkCapabilitiesValid(): Boolean = when {
    this == null -> false
    hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
            (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) -> true

    else -> false
}

fun CurrentNetwork.isConnected(): Boolean {
    return isListening &&
            isAvailable &&
            networkCapabilities.isNetworkCapabilitiesValid()
}

fun ApiMovieItem.toMovieItem() = MovieItem(
    imdbId = imdbID,
    year = year,
    title = title,
    type = type,
    poster = poster,
    isSaved = false
)

fun ApiMovieDetails.toMovieDetails() = MovieDetails(
    imdbId = imdbID,
    actors = actors,
    awards = awards,
    boxOffice = boxOffice,
    country = country,
    director = director,
    dvd = dVD,
    genre = genre,
    imdbRating = imdbRating,
    imdbVotes = imdbVotes,
    language = language,
    metaScore = metascore,
    plot = plot,
    poster = poster,
    production = production,
    rated = rated,
    ratings = ratings.map { it.toRatings(imdbID) },
    released = released,
    runtime = runtime,
    title = title,
    type = type,
    website = website,
    writer = writer,
    year = year
)

fun ApiRating.toRatings(imdbId: String) = Rating(
    imdbId = imdbId,
    source = source,
    value = value
)
