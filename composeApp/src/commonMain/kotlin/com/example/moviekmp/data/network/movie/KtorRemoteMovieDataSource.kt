package com.example.moviekmp.data.network.movie

import com.example.moviekmp.core.AppSecrets
import com.example.moviekmp.core.data.dto.movies.MovieResponseDto
import com.example.moviekmp.core.data.safeCall
import com.example.moviekmp.core.domain.DataError
import com.example.moviekmp.core.domain.NetworkResult
import com.example.moviekmp.data.network.movie.RemoteMovieDataSource
import com.example.moviekmp.shareCore.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorRemoteMovieDataSource(
    private val httpClient: HttpClient
): RemoteMovieDataSource {


    override suspend fun getTrendingMovies(timeWindow: String): NetworkResult<MovieResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}trending/movie/$timeWindow") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
            }
        }
    }

    override suspend fun getNowPlayingMovies(page: Int?): NetworkResult<MovieResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}movie/now_playing") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
                url {
                    parameters.append("page", page.toString())
                }
            }
        }
    }

    override suspend fun getPopularMovies(page: Int?): NetworkResult<MovieResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}movie/popular") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
                url {
                    parameters.append("page", page.toString())
                }
            }
        }
    }

    override suspend fun getTopRatedMovies(page: Int?): NetworkResult<MovieResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}movie/top_rated") {
                headers {
                    append("Authorization", "Bearer ${AppSecrets.accessToken}")
                }
                url {
                    parameters.append("page", page.toString())
                }
            }
        }
    }

    override suspend fun getUpcomingMovies(page: Int?): NetworkResult<MovieResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("${Constants.Companion.BASE_URL}movie/upcoming") {
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