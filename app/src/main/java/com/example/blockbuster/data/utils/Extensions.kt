package com.example.blockbuster.data.utils

import android.net.NetworkCapabilities
import com.example.blockbuster.data.local.entities.MovieItem
import com.example.blockbuster.data.network.CurrentNetwork
import com.example.blockbuster.data.remote.model.ApiMovieItem

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
    poster = poster
)