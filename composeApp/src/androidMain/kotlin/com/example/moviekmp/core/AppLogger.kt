package com.example.moviekmp.core

import android.util.Log

actual object AppLogger {
    actual fun logDebug(tag: String, message: String){
        Log.d(tag, message)
    }

    actual fun logError(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    actual fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }

    actual fun logVerbose(tag: String, message: String) {
        Log.v(tag, message)
    }
}