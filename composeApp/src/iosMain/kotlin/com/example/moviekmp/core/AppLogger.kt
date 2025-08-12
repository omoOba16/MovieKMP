package com.example.moviekmp.core

actual object AppLogger {

    actual fun logDebug(tag: String, message: String){
        println("$tag: $message")
    }

    actual fun logError(tag: String, message: String, throwable: Throwable?) {
        val errorDetails = throwable?.let { ": ${it.message ?: ""} ${it.stackTraceToString()}" } ?: ""
        println("$tag: $message $errorDetails")
    }

    actual fun logInfo(tag: String, message: String) {
        println("$tag: $message")
    }

    actual fun logVerbose(tag: String, message: String) {
        println("$tag: $message")
    }
}