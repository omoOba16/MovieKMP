package com.example.moviekmp.core.data.repository

import com.example.moviekmp.core.data.dto.shows.ShowResponseDto
import com.example.moviekmp.core.domain.DataError
import com.example.moviekmp.core.domain.NetworkResult
import com.example.moviekmp.data.network.show.RemoteShowDataSource
import com.example.moviekmp.data.repository.ShowRepository
import com.example.moviekmp.domain.model.Show

class ShowRepositoryImp(
    private val remoteShowDataSource: RemoteShowDataSource
): ShowRepository {
    override suspend fun getTrendingShows(timeWindow: String): NetworkResult<List<Show>?, DataError.Remote> {
        val response = remoteShowDataSource.getTrendingShows(timeWindow)
        return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data.results?.map {
                        Show(
                            id = it?.id ?: 0,
                            title = it?.name ?: "",
                            overview = it?.overview ?: "",
                            posterPath = it?.posterPath ?: ""
                        )
                    }
                )
            }
        }
    }

    override suspend fun getAiringToday(page: Int?): NetworkResult<ShowResponseDto?, DataError.Remote> {
        val response = remoteShowDataSource.getAiringToday(page)
        return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data
                )
            }
        }
    }

    override suspend fun getOnTheAir(page: Int?): NetworkResult<ShowResponseDto?, DataError.Remote> {
        val response = remoteShowDataSource.getOnTheAir(page)
        return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data
                )
            }
        }
    }

    override suspend fun getPopularShows(page: Int?): NetworkResult<ShowResponseDto?, DataError.Remote> {
        val response = remoteShowDataSource.getPopularShows(page)
        return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data
                )
            }
        }
    }

    override suspend fun getTopRatedShows(page: Int?): NetworkResult<ShowResponseDto?, DataError.Remote> {
        val response = remoteShowDataSource.getTopRatedShows(page)
        return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data
                )
            }
        }
    }
}