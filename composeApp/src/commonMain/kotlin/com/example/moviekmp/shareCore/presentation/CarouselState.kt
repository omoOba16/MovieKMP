package com.example.moviekmp.shareCore.presentation

import com.example.moviekmp.domain.model.Carousel

data class CarouselState(
    val isLoading: Boolean = false,
    val carousels: List<Carousel> = emptyList(),
    val error: String? = null
)
