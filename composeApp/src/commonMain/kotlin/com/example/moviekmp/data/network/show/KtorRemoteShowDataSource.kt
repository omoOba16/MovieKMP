package com.example.moviekmp.data.network.show

import com.example.moviekmp.core.AppSecrets
import com.example.moviekmp.core.data.dto.shows.ShowResponseDto
import com.example.moviekmp.core.data.safeCall
import com.example.moviekmp.core.domain.DataError
import com.example.moviekmp.core.domain.NetworkResult
import com.example.moviekmp.shareCore.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorRemoteShowDataSource(
    private val httpClient: HttpClient
): RemoteShowDataSource {

    override suspend fun getTrendingShows(timeWindow: String): NetworkResult<ShowResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}trending/tv/$timeWindow") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
            }
        }
    }

    override suspend fun getAiringToday(page: Int?): NetworkResult<ShowResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}tv/airing_today") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
                url {
                    parameters.append("page", page.toString())
                }
            }
        }
    }

    override suspend fun getOnTheAir(page: Int?): NetworkResult<ShowResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}tv/on_the_air") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
                url {
                    parameters.append("page", page.toString())
                }
            }
        }
    }

    override suspend fun getPopularShows(page: Int?): NetworkResult<ShowResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}tv/popular") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
                url {
                    parameters.append("page", page.toString())
                }
            }
        }
    }

    override suspend fun getTopRatedShows(page: Int?): NetworkResult<ShowResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}tv/top_rated") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
                url {
                    parameters.append("page", page.toString())
                }
            }
        }
    }
}