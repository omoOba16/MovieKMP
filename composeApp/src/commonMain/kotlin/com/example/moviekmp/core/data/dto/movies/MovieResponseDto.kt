package com.example.moviekmp.core.data.dto.movies

import com.example.moviekmp.core.data.dto.movies.ResultMoviesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    @SerialName("page") val page: Int? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("results") val results: List<ResultMoviesDto?>? = null,
    @SerialName("total_results") val totalResults: Int? = null
)