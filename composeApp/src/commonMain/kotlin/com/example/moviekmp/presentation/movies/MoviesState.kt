package com.example.moviekmp.presentation.movies

import com.example.moviekmp.domain.model.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    val hasMoreData: Boolean = false,
    val categoryName: String? = null
)