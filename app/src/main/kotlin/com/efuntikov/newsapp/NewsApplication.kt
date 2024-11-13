package com.efuntikov.newsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import com.efuntikov.newsapp.BuildConfig

@HiltAndroidApp
class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
