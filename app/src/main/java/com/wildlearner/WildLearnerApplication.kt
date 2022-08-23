package com.wildlearner

import android.app.Application
import com.wildlearner.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WildLearnerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WildLearnerApplication)
            modules(
                listOf(
                    searchModule
                )
            )
        }
        instance = this
    }

    companion object {
        lateinit var instance: WildLearnerApplication
            private set
    }

}