package com.wildlearner.network

import com.wildlearner.util.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("X-RapidAPI-Key", API_KEY).build()
        return chain.proceed(authenticatedRequest)
    }

}