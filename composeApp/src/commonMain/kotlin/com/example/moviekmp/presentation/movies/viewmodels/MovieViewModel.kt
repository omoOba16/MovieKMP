package com.example.moviekmp.presentation.movies.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviekmp.core.AppLogger
import com.example.moviekmp.core.domain.NetworkResult
import com.example.moviekmp.data.repository.MovieRepository
import com.example.moviekmp.domain.model.Carousel
import com.example.moviekmp.domain.model.Movie
import com.example.moviekmp.presentation.movies.MoviesState
import com.example.moviekmp.shareCore.presentation.CarouselState
import com.example.moviekmp.shareCore.presentation.navigation.MovieCategory
import com.example.moviekmp.shareCore.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_ORIGINAL
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    private var nowPlayingPage by mutableIntStateOf(1)
    private var popularPage by mutableIntStateOf(1)
    private var topRatedPage by mutableIntStateOf(1)
    private var upcomingPage by mutableIntStateOf(1)


    private val _trendingMovies = MutableStateFlow(CarouselState())
    val trendingMovies = _trendingMovies
        .onStart { getTrendingMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _trendingMovies.value
        )

    private val _nowPlayingMovies = MutableStateFlow(MoviesState())
    val nowPlayingMovies = _nowPlayingMovies
        .onStart { getNowPlayingMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _nowPlayingMovies.value
        )

    private val _popularMovies = MutableStateFlow(MoviesState())
    val popularMovies = _popularMovies
        .onStart { getPopularMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _popularMovies.value
        )

    private val _topRatedMovies = MutableStateFlow(MoviesState())
    val topRatedMovies = _topRatedMovies
        .onStart { getTopRatedMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _topRatedMovies.value
        )

    private val _upComingMovies = MutableStateFlow(MoviesState())
    val upComingMovies = _upComingMovies
        .onStart { getUpcomingMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _upComingMovies.value
        )


    fun getTrendingMovies() = viewModelScope.launch {
        _trendingMovies.update {
            it.copy(isLoading = true)
        }

        val response = movieRepository.getTrendingMovies("day")
        when (response) {
            is NetworkResult.Error -> {
                _trendingMovies.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val carousels = response.data?.map {
                    Carousel(
                        id = it.id ?: 0,
                        posterPath = "${MOVIE_IMAGE_POSTER_SIZE_ORIGINAL}${it.posterPath}"
                    )
                }
                _trendingMovies.update {
                    it.copy(
                        isLoading = false,
                        carousels = carousels ?: emptyList()
                    )
                }
            }
        }
    }

    fun getNowPlayingMovies() = viewModelScope.launch {
        _nowPlayingMovies.update {
            it.copy(isLoading = true)
        }

        val response = movieRepository.getNowPlayingMovies(nowPlayingPage)
        when (response) {
            is NetworkResult.Error -> {
                _nowPlayingMovies.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val totalPages = response.data?.totalPages ?: 1
                val movies = response.data?.results?.map {
                    Movie(
                        id = it?.id ?: 0,
                        title = it?.title ?: "",
                        overview = it?.overview ?: "",
                        posterPath = it?.posterPath ?: ""
                    )
                }

                _nowPlayingMovies.update {
                    val existingMovies = it.movies
                    val allMovies = existingMovies + (movies ?: emptyList())
                    val distinctMovies = allMovies.distinctBy { it -> it.id }
                    it.copy(
                        movies = distinctMovies,
                        hasMoreData = nowPlayingPage < totalPages,
                        isLoading = false
                    )
                }

                if(nowPlayingPage != totalPages){
                    nowPlayingPage++
                }
            }
        }
    }

    fun getPopularMovies() = viewModelScope.launch {
        _popularMovies.update {
            it.copy(isLoading = true)
        }

        val response = movieRepository.getPopularMovies(popularPage)
        when (response) {
            is NetworkResult.Error -> {
                _popularMovies.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val totalPages = response.data?.totalPages ?: 1
                val movies = response.data?.results?.map {
                    Movie(
                        id = it?.id ?: 0,
                        title = it?.title ?: "",
                        overview = it?.overview ?: "",
                        posterPath = it?.posterPath ?: ""
                    )
                }

                _popularMovies.update {
                    val existingMovies = it.movies
                    val allMovies = existingMovies + (movies ?: emptyList())
                    val distinctMovies = allMovies.distinctBy { it -> it.id }
                    it.copy(
                        movies = distinctMovies,
                        hasMoreData = popularPage < totalPages,
                        isLoading = false
                    )
                }

                if(popularPage != totalPages){
                    popularPage++
                }
            }
        }
    }

    fun getTopRatedMovies() = viewModelScope.launch {
        _topRatedMovies.update {
            it.copy(isLoading = true)
        }

        val response = movieRepository.getTopRatedMovies(topRatedPage)
        when (response) {
            is NetworkResult.Error -> {
                _topRatedMovies.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val totalPages = response.data?.totalPages ?: 1
                val movies = response.data?.results?.map {
                    Movie(
                        id = it?.id ?: 0,
                        title = it?.title ?: "",
                        overview = it?.overview ?: "",
                        posterPath = it?.posterPath ?: ""
                    )
                }

                _topRatedMovies.update {
                    val existingMovies = it.movies
                    val allMovies = existingMovies + (movies ?: emptyList())
                    val distinctMovies = allMovies.distinctBy { it -> it.id }
                    it.copy(
                        movies = distinctMovies,
                        hasMoreData = topRatedPage < totalPages,
                        isLoading = false
                    )
                }

                if(topRatedPage != totalPages){
                    topRatedPage++
                }
            }
        }
    }

    fun getUpcomingMovies() = viewModelScope.launch {
        _upComingMovies.update {
            it.copy(isLoading = true)
        }

        val response = movieRepository.getUpcomingMovies(upcomingPage)
        when (response) {
            is NetworkResult.Error -> {
                _upComingMovies.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val totalPages = response.data?.totalPages ?: 1
                val movies = response.data?.results?.map {
                    Movie(
                        id = it?.id ?: 0,
                        title = it?.title ?: "",
                        overview = it?.overview ?: "",
                        posterPath = it?.posterPath ?: ""
                    )
                }

                _upComingMovies.update {
                    val existingMovies = it.movies
                    val allMovies = existingMovies + (movies ?: emptyList())
                    val distinctMovies = allMovies.distinctBy { it -> it.id }
                    it.copy(
                        movies = distinctMovies,
                        hasMoreData = upcomingPage < totalPages,
                        isLoading = false
                    )
                }

                if(upcomingPage != totalPages){
                    upcomingPage++
                }
            }
        }
    }


}