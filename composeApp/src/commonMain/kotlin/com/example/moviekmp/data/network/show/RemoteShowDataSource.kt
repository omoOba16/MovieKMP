package com.example.moviekmp.data.network.show

import com.example.moviekmp.core.data.dto.shows.ShowResponseDto
import com.example.moviekmp.core.domain.DataError
import com.example.moviekmp.core.domain.NetworkResult

interface RemoteShowDataSource {
    suspend fun getTrendingShows(timeWindow: String): NetworkResult<ShowResponseDto, DataError.Remote>
    suspend fun getAiringToday(page:Int?): NetworkResult<ShowResponseDto, DataError.Remote>
    suspend fun getOnTheAir(page:Int?): NetworkResult<ShowResponseDto, DataError.Remote>
    suspend fun getPopularShows(page:Int?): NetworkResult<ShowResponseDto, DataError.Remote>
    suspend fun getTopRatedShows(page:Int?): NetworkResult<ShowResponseDto, DataError.Remote>
}