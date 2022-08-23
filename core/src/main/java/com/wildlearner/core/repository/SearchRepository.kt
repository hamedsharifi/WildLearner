package com.wildlearner.core.repository

import com.wildlearner.core.model.SearchModel


interface SearchRepository {

    suspend fun googleSearch(query: String): SearchModel
}
