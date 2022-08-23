package com.wildlearner.baseviewmodel

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {

    fun bgDispatcher():CoroutineDispatcher
    fun uiDispatcher():CoroutineDispatcher
    fun ioDispatcher():CoroutineDispatcher
    fun immediateDispatcher(): CoroutineDispatcher=uiDispatcher()
}