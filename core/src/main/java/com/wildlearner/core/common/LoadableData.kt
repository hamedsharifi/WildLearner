package com.wildlearner.core.common


sealed class LoadableData<out T> {
    abstract val data: T?

    fun onLoading(function: () -> Unit): LoadableData<T> {
        if (this is Loading) function()
        return this
    }

    fun onFailed(function: (throwable: Throwable, title: String?) -> Unit): LoadableData<T> {
        if (this is Failed) function(throwble, title)
        return this
    }
}

data class Loaded<T>(override val data: T) : LoadableData<T>()

data class Failed<T>(val throwble: Throwable, val title: String? = null) : LoadableData<T>() {
    override val data: T?
        get() = null
}

object Loading : LoadableData<Nothing>() {
    override val data: Nothing?
        get() = null
}

object NotLoaded : LoadableData<Nothing>() {
    override val data: Nothing?
        get() = null
}
