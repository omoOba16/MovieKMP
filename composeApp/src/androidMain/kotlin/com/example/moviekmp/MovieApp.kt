package com.example.moviekmp

import android.app.Application
import com.example.moviekmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MovieApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MovieApp)
        }
    }
}