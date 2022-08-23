package com.wildlearner.core.common

import kotlinx.coroutines.CoroutineDispatcher

//Used in tests (CoroutineDispatcherProviderCore)
interface CoroutineDispatcherProviderCore {

    fun bgDispatcher(): CoroutineDispatcher
    fun uiDispatcher(): CoroutineDispatcher
    fun ioDispatcher(): CoroutineDispatcher
    fun immediateDispatcher(): CoroutineDispatcher = uiDispatcher()
}