package com.example.moviekmp.core

expect object AppLogger {
    fun logDebug(tag: String, message: String)
    fun logError(tag: String, message: String, throwable: Throwable? = null)
    fun logInfo(tag: String, message: String)
    fun logVerbose(tag: String, message: String)
}