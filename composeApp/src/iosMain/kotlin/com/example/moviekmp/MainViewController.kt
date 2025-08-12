package com.example.moviekmp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.moviekmp.di.initKoin
import com.example.moviekmp.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }