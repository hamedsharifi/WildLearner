package com.wildlearner.core.usecase

import com.wildlearner.core.repository.SearchRepository

class SearchUseCase(private val repository: SearchRepository) {

    suspend fun execute(query: String) = repository.googleSearch(query)
}