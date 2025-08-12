package com.example.moviekmp.presentation.shows.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviekmp.core.AppLogger
import com.example.moviekmp.core.domain.NetworkResult
import com.example.moviekmp.data.repository.ShowRepository
import com.example.moviekmp.domain.model.Carousel
import com.example.moviekmp.domain.model.Show
import com.example.moviekmp.presentation.shows.ShowsState
import com.example.moviekmp.shareCore.presentation.CarouselState
import com.example.moviekmp.shareCore.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_ORIGINAL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShowViewModel(
    private val showRepository: ShowRepository
): ViewModel() {

    private var airingTodayPage by mutableIntStateOf(1)
    private var onTheAirPage by mutableIntStateOf(1)
    private var popularPage by mutableIntStateOf(1)
    private var topRatedPage by mutableIntStateOf(1)

    private val _trendingShows = MutableStateFlow(CarouselState())
    val trendingShows = _trendingShows
        .onStart { getTrendingShows() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _trendingShows.value
        )

    private val _airingTodayShows = MutableStateFlow(ShowsState())
    val airingTodayShows = _airingTodayShows
        .onStart { getAiringTodayShows() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _airingTodayShows.value
        )

    private val _onTheAirShows = MutableStateFlow(ShowsState())
    val onTheAirShows = _onTheAirShows
        .onStart { getOnTheAirShows() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _onTheAirShows.value
        )

    private val _popularShows = MutableStateFlow(ShowsState())
    val popularShows = _popularShows
        .onStart { getPopularShows() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _popularShows.value
        )

    private val _topRatedShows = MutableStateFlow(ShowsState())
    val topRatedShows = _topRatedShows
        .onStart { getTopRatedShows() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _topRatedShows.value
        )

    fun getTrendingShows() = viewModelScope.launch {
        _trendingShows.update {
            it.copy(isLoading = true)
        }

        val response = showRepository.getTrendingShows("day")
        when (response) {
            is NetworkResult.Error -> {
                _trendingShows.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val carousels = response.data?.map {
                    Carousel(
                        id = it.id,
                        posterPath = "${MOVIE_IMAGE_POSTER_SIZE_ORIGINAL}${it.posterPath}"
                    )
                }
                AppLogger.logDebug("TrendingShows", "${carousels}")
                _trendingShows.update {
                    it.copy(isLoading = false, carousels = carousels ?: emptyList())
                }
            }
        }

    }

    fun getAiringTodayShows() = viewModelScope.launch {
        _airingTodayShows.update {
            it.copy(isLoading = true)
        }

        val response = showRepository.getAiringToday(airingTodayPage)
        when (response) {
            is NetworkResult.Error -> {
                _airingTodayShows.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val totalPages = response.data?.totalPages ?: 1
                val shows = response.data?.results?.map {
                    Show(
                        id = it?.id ?: 0,
                        title = it?.name ?: "",
                        overview = it?.overview ?: "",
                        posterPath = it?.posterPath ?: ""
                    )
                }

                _airingTodayShows.update {
                    val existingShows = it.shows
                    val allShows = existingShows + (shows ?: emptyList())
                    val distinctShows = allShows.distinctBy { it -> it.id }
                    it.copy(
                        shows = distinctShows,
                        hasMoreData = airingTodayPage < totalPages,
                        isLoading = false
                    )
                }

                if(airingTodayPage != totalPages){
                    airingTodayPage++
                }
            }
        }
    }

    fun getOnTheAirShows() = viewModelScope.launch {
        _onTheAirShows.update {
            it.copy(isLoading = true)
        }

        val response = showRepository.getOnTheAir(onTheAirPage)
        when (response) {
            is NetworkResult.Error -> {
                _onTheAirShows.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val totalPages = response.data?.totalPages ?: 1
                val shows = response.data?.results?.map {
                    Show(
                        id = it?.id ?: 0,
                        title = it?.name ?: "",
                        overview = it?.overview ?: "",
                        posterPath = it?.posterPath ?: ""
                    )
                }

                _onTheAirShows.update {
                    val existingShows = it.shows
                    val allShows = existingShows + (shows ?: emptyList())
                    val distinctShows = allShows.distinctBy { it -> it.id }
                    it.copy(
                        shows = distinctShows,
                        hasMoreData = onTheAirPage < totalPages,
                        isLoading = false
                    )
                }

                if(onTheAirPage != totalPages){
                    onTheAirPage++
                }
            }
        }
    }

    fun getPopularShows() = viewModelScope.launch {
        _popularShows.update {
            it.copy(isLoading = true)
        }

        val response = showRepository.getPopularShows(popularPage)
        when (response) {
            is NetworkResult.Error -> {
                _popularShows.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val totalPages = response.data?.totalPages ?: 1
                val shows = response.data?.results?.map {
                    Show(
                        id = it?.id ?: 0,
                        title = it?.name ?: "",
                        overview = it?.overview ?: "",
                        posterPath = it?.posterPath ?: ""
                    )
                }

                _popularShows.update {
                    val existingShows = it.shows
                    val allShows = existingShows + (shows ?: emptyList())
                    val distinctShows = allShows.distinctBy { it -> it.id }
                    it.copy(
                        shows = distinctShows,
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

    fun getTopRatedShows() = viewModelScope.launch {
        _topRatedShows.update {
            it.copy(isLoading = true)
        }

        val response = showRepository.getPopularShows(topRatedPage)
        when (response) {
            is NetworkResult.Error -> {
                _topRatedShows.update {
                    it.copy(isLoading = false, error = it.error)
                }
            }

            is NetworkResult.Success -> {
                val totalPages = response.data?.totalPages ?: 1
                val shows = response.data?.results?.map {
                    Show(
                        id = it?.id ?: 0,
                        title = it?.name ?: "",
                        overview = it?.overview ?: "",
                        posterPath = it?.posterPath ?: ""
                    )
                }

                _topRatedShows.update {
                    val existingShows = it.shows
                    val allShows = existingShows + (shows ?: emptyList())
                    val distinctShows = allShows.distinctBy { it -> it.id }
                    it.copy(
                        shows = distinctShows,
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


}