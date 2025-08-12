package com.example.moviekmp.presentation.movies.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviekmp.core.domain.NetworkResult
import com.example.moviekmp.data.repository.MovieRepository
import com.example.moviekmp.domain.model.Movie
import com.example.moviekmp.presentation.movies.MoviesState
import com.example.moviekmp.shareCore.presentation.navigation.MovieCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryMoviesViewModel(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var page by mutableIntStateOf(1)

    private val category = savedStateHandle.get<String>("categoryName") ?: ""

    private val _allMovies = MutableStateFlow(MoviesState())
    val allMovies = _allMovies
        .onStart {
            getMovies()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _allMovies.value
        )

    fun getMovies(){
        when(MovieCategory.fromApiValue(category)){
            MovieCategory.NOW_PLAYING -> getNowPlayingMovies()
            MovieCategory.POPULAR -> getPopularMovies()
            MovieCategory.TOP_RATED -> getTopRatedMovies()
            MovieCategory.UPCOMING -> getUpcomingMovies()
            null -> {}
        }
    }

    fun getNowPlayingMovies() = viewModelScope.launch {
        _allMovies.update {
            it.copy(
                categoryName = MovieCategory.NOW_PLAYING.title,
                isLoading = true
            )
        }

        val response = movieRepository.getNowPlayingMovies(page)
        when (response) {
            is NetworkResult.Error -> {
                _allMovies.update {
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

                _allMovies.update {
                    val existingMovies = it.movies
                    val allMovies = existingMovies + (movies ?: emptyList())
                    val distinctMovies = allMovies.distinctBy { it -> it.id }
                    it.copy(
                        movies = distinctMovies,
                        hasMoreData = page < totalPages,
                        isLoading = false
                    )
                }

                if(page != totalPages){
                    page++
                }
            }
        }
    }

    fun getPopularMovies() = viewModelScope.launch {
        _allMovies.update {
            it.copy(
                categoryName = MovieCategory.POPULAR.title,
                isLoading = true
            )
        }

        val response = movieRepository.getPopularMovies(page)
        when (response) {
            is NetworkResult.Error -> {
                _allMovies.update {
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

                _allMovies.update {
                    val existingMovies = it.movies
                    val allMovies = existingMovies + (movies ?: emptyList())
                    val distinctMovies = allMovies.distinctBy { it -> it.id }
                    it.copy(
                        movies = distinctMovies,
                        hasMoreData = page < totalPages,
                        isLoading = false
                    )
                }

                if(page != totalPages){
                    page++
                }
            }
        }
    }

    fun getTopRatedMovies() = viewModelScope.launch {
        _allMovies.update {
            it.copy(
                categoryName = MovieCategory.TOP_RATED.title,
                isLoading = true
            )
        }

        val response = movieRepository.getTopRatedMovies(page)
        when (response) {
            is NetworkResult.Error -> {
                _allMovies.update {
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

                _allMovies.update {
                    val existingMovies = it.movies
                    val allMovies = existingMovies + (movies ?: emptyList())
                    val distinctMovies = allMovies.distinctBy { it -> it.id }
                    it.copy(
                        movies = distinctMovies,
                        hasMoreData = page < totalPages,
                        isLoading = false
                    )
                }

                if(page != totalPages){
                    page++
                }
            }
        }
    }

    fun getUpcomingMovies() = viewModelScope.launch {
        _allMovies.update {
            it.copy(
                categoryName = MovieCategory.UPCOMING.title,
                isLoading = true
            )
        }

        val response = movieRepository.getUpcomingMovies(page)
        when (response) {
            is NetworkResult.Error -> {
                _allMovies.update {
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

                _allMovies
                    .update {
                    val existingMovies = it.movies
                    val allMovies = existingMovies + (movies ?: emptyList())
                    val distinctMovies = allMovies.distinctBy { it -> it.id }
                    it.copy(
                        movies = distinctMovies,
                        hasMoreData = page < totalPages,
                        isLoading = false
                    )
                }

                if(page != totalPages){
                    page++
                }
            }
        }
    }
}