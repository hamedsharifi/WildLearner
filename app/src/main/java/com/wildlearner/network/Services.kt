package com.wildlearner.network

import com.wildlearner.core.model.SearchModel
import com.wildlearner.util.BASEURL

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Services {

    @GET("/api/v1/search/q={query}")
    suspend fun search(@Path("query") query: String?): SearchModel




    companion object {
        private var retrofitService: Services? = null
        private var okHttpClient: OkHttpClient =
            OkHttpClient().newBuilder().addNetworkInterceptor(AuthInterceptor())
                .build()

        fun getInstance(): Services {
            return retrofitService ?: Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Services::class.java)
        }
    }

}