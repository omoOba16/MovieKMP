package com.example.moviekmp.core.data.dto.shows

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowResponseDto(
    @SerialName("page") val page: Int? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("results") val results: List<ResultShowsDto?>? = null,
    @SerialName("total_results") val totalResults: Int? = null
)
