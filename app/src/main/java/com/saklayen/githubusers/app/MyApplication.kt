package com.saklayen.githubusers.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.saklayen.githubusers.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.reword.RewordInterceptor
import dev.b3nedikt.viewpump.ViewPump
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        initTimber()
        Restring.init(this)
        ViewPump.init(RewordInterceptor)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
