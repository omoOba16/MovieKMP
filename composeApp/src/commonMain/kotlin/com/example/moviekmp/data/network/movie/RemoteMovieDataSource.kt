package com.example.moviekmp.data.network.movie

import com.example.moviekmp.core.data.dto.movies.MovieResponseDto
import com.example.moviekmp.core.domain.DataError
import com.example.moviekmp.core.domain.NetworkResult

interface RemoteMovieDataSource {

    suspend fun getTrendingMovies(timeWindow: String): NetworkResult<MovieResponseDto, DataError.Remote>
    suspend fun getNowPlayingMovies(page:Int?): NetworkResult<MovieResponseDto, DataError.Remote>
    suspend fun getPopularMovies(page:Int?): NetworkResult<MovieResponseDto, DataError.Remote>
    suspend fun getTopRatedMovies(page:Int?): NetworkResult<MovieResponseDto, DataError.Remote>
    suspend fun getUpcomingMovies(page:Int?): NetworkResult<MovieResponseDto, DataError.Remote>

}