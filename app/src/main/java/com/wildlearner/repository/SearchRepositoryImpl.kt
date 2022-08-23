package com.wildlearner.repository

import com.wildlearner.core.repository.SearchRepository
import com.wildlearner.network.Services
import com.wildlearner.core.model.SearchModel

class SearchRepositoryImpl(private val retrofitService: Services): SearchRepository {

    override suspend fun googleSearch(query: String): SearchModel {
        return retrofitService.search(query)
    }
}