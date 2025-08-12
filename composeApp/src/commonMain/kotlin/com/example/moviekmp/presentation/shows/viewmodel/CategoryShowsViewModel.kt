package com.example.moviekmp.presentation.shows.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviekmp.core.domain.NetworkResult
import com.example.moviekmp.data.repository.ShowRepository
import com.example.moviekmp.domain.model.Show
import com.example.moviekmp.presentation.shows.ShowsState
import com.example.moviekmp.shareCore.presentation.navigation.Screens
import com.example.moviekmp.shareCore.presentation.navigation.ShowCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryShowsViewModel(
    private val showRepository: ShowRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var page by mutableIntStateOf(1)

    private val category = savedStateHandle.get<String>("categoryName") ?: ""

    private val _allShows = MutableStateFlow(ShowsState())
    val allShows = _allShows
        .onStart {
            getShows()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _allShows.value
        )

    fun getShows(){
        when(ShowCategory.fromApiValue(category)){
            ShowCategory.AIRING_TODAY -> getAiringTodayShows()
            ShowCategory.ON_THE_AIR -> getOnTheAirShows()
            ShowCategory.POPULAR -> getPopularShows()
            ShowCategory.TOP_RATED -> getTopRatedShows()
            null -> {}
        }
    }

    fun getAiringTodayShows() = viewModelScope.launch {
        _allShows.update {
            it.copy(
                categoryName = ShowCategory.AIRING_TODAY.title,
                isLoading = true
            )
        }

        val response = showRepository.getAiringToday(page)
        when (response) {
            is NetworkResult.Error -> {
                _allShows.update {
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

                _allShows.update {
                    val existingShows = it.shows
                    val allShows = existingShows + (shows ?: emptyList())
                    val distinctShows = allShows.distinctBy { it -> it.id }
                    it.copy(
                        shows = distinctShows,
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

    fun getOnTheAirShows() = viewModelScope.launch {
        _allShows.update {
            it.copy(
                categoryName = ShowCategory.ON_THE_AIR.title,
                isLoading = true
            )
        }

        val response = showRepository.getOnTheAir(page)
        when (response) {
            is NetworkResult.Error -> {
                _allShows.update {
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

                _allShows.update {
                    val existingShows = it.shows
                    val allShows = existingShows + (shows ?: emptyList())
                    val distinctShows = allShows.distinctBy { it -> it.id }
                    it.copy(
                        shows = distinctShows,
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

    fun getPopularShows() = viewModelScope.launch {
        _allShows.update {
            it.copy(
                categoryName = ShowCategory.POPULAR.title,
                isLoading = true
            )
        }

        val response = showRepository.getPopularShows(page)
        when (response) {
            is NetworkResult.Error -> {
                _allShows.update {
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

                _allShows.update {
                    val existingShows = it.shows
                    val allShows = existingShows + (shows ?: emptyList())
                    val distinctShows = allShows.distinctBy { it -> it.id }
                    it.copy(
                        shows = distinctShows,
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

    fun getTopRatedShows() = viewModelScope.launch {
        _allShows.update {
            it.copy(
                categoryName = ShowCategory.TOP_RATED.title,
                isLoading = true)
        }

        val response = showRepository.getPopularShows(page)
        when (response) {
            is NetworkResult.Error -> {
                _allShows.update {
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

                _allShows.update {
                    val existingShows = it.shows
                    val allShows = existingShows + (shows ?: emptyList())
                    val distinctShows = allShows.distinctBy { it -> it.id }
                    it.copy(
                        shows = distinctShows,
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