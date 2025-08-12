package com.example.moviekmp.core

import com.example.moviekmp.BuildConfig

actual object AppSecrets {
    actual val accessToken: String
        get() =  BuildConfig.accessToken
}