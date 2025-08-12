package com.example.moviekmp.core.data.repository

import com.example.moviekmp.core.data.dto.movies.MovieResponseDto
import com.example.moviekmp.core.domain.DataError
import com.example.moviekmp.core.domain.NetworkResult
import com.example.moviekmp.data.network.movie.RemoteMovieDataSource
import com.example.moviekmp.data.repository.MovieRepository
import com.example.moviekmp.domain.model.Movie

class MovieRepositoryImp(
    private val remoteMovieDataSource: RemoteMovieDataSource
): MovieRepository {

    override suspend fun getTrendingMovies(timeWindow: String): NetworkResult<List<Movie>?, DataError.Remote> {
       val response = remoteMovieDataSource.getTrendingMovies(timeWindow)
       return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data.results?.map {
                        Movie(
                            id = it?.id ?: 0,
                            title = it?.title ?: "",
                            overview = it?.overview ?: "",
                            posterPath = it?.posterPath ?: ""
                        )
                    }
                )
            }
       }
    }

    override suspend fun getNowPlayingMovies(page: Int?): NetworkResult<MovieResponseDto, DataError.Remote> {
        val response = remoteMovieDataSource.getNowPlayingMovies(page)
        return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data
                )
            }
        }
    }

    override suspend fun getPopularMovies(page: Int?): NetworkResult<MovieResponseDto, DataError.Remote> {
        val response = remoteMovieDataSource.getPopularMovies(page)
        return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data
                )
            }
        }
    }

    override suspend fun getTopRatedMovies(page: Int?): NetworkResult<MovieResponseDto, DataError.Remote> {
        val response = remoteMovieDataSource.getTopRatedMovies(page)
        return when(response) {
            is NetworkResult.Error -> NetworkResult.Error(response.error)
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    response.data
                )
            }
        }
    }

    override suspend fun getUpcomingMovies(page: Int?): NetworkResult<MovieResponseDto, DataError.Remote> {
        val response = remoteMovieDataSource.getUpcomingMovies(page)
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