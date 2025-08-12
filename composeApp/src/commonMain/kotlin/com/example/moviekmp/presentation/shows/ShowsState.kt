package com.example.moviekmp.presentation.shows

import com.example.moviekmp.domain.model.Show

data class ShowsState(
    val isLoading: Boolean = false,
    val shows: List<Show> = emptyList(),
    val error: String = "",
    val hasMoreData: Boolean = false,
    val categoryName: String? = null
)
