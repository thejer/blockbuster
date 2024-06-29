package com.example.blockbuster.data.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            throw NetworkException("Timeout occurred", e)
        } catch (e: IOException) {
            throw NetworkException("Timeout occurred", e)
        }
    }
}

class NetworkException(s: String, e: IOException) : IOException(s, e)