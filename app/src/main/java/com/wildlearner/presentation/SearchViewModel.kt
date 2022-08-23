package com.wildlearner.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wildlearner.baseviewmodel.BaseViewModel
import com.wildlearner.baseviewmodel.CoroutineDispatcherProvider
import com.wildlearner.core.model.SearchModel
import com.wildlearner.core.usecase.SearchUseCase

import com.wildlearner.util.API_KEY
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchViewModel(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val useCase: SearchUseCase,
) : BaseViewModel(
    coroutineContexts = coroutineDispatcherProvider
) {
    private val _searchResults = MutableLiveData<SearchModel>()
    val searchResults: LiveData<SearchModel>
        get() = _searchResults

    private fun onError(message: String) {
        _errorMessage.postValue(message)
        _loading.postValue(false)
    }

    fun search(query: String) {
        viewModelScope.launch {
            withContext(ioDispatcher()) {
                lateinit var response: SearchModel
                _loading.postValue(true)
                runCatching {
                    response = useCase.execute(query)
                    println("true")
                }.onSuccess {
                    println(response.toString())
                    _searchResults.postValue(response)
                    _loading.postValue(false)
                }.onFailure {
                    it.message?.let { notNullThrowable -> onError(notNullThrowable) }
                }
            }
        }
    }

}