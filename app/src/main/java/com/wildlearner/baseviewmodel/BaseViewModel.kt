package com.wildlearner.baseviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
    protected val coroutineContexts: CoroutineDispatcherProvider
) : ViewModel(), CoroutineScope {

    open val _errorMessage = MutableLiveData<String>()
    open val errorMessage: LiveData<String>
        get() = _errorMessage

    open val _loading = MutableLiveData<Boolean>()
    open val loading: LiveData<Boolean>
        get() = _loading

    private var job = SupervisorJob()

    private val scope = CoroutineScope(mainDispatcher() + job)

    fun bgDispatcher(): CoroutineDispatcher {
        return coroutineContexts.bgDispatcher()
    }

    fun mainDispatcher(): CoroutineDispatcher {
        return coroutineContexts.uiDispatcher()
    }

    fun immediateDispatcher(): CoroutineDispatcher {
        return coroutineContexts.immediateDispatcher()
    }

    fun ioDispatcher(): CoroutineDispatcher {
        return coroutineContexts.ioDispatcher()
    }

    override val coroutineContext: CoroutineContext
        get() = scope.coroutineContext

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

}

fun coroutineDispatcherProvider() = object : CoroutineDispatcherProvider {

    override fun bgDispatcher(): CoroutineDispatcher = Dispatchers.Default

    override fun uiDispatcher(): CoroutineDispatcher = Dispatchers.Main

    override fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    override fun immediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate


}