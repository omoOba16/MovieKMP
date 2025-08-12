package com.example.moviekmp.presentation.shows

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.moviekmp.presentation.shows.components.ShowsEndlessLazyRowSection
import com.example.moviekmp.presentation.shows.viewmodel.ShowViewModel
import com.example.moviekmp.shareCore.presentation.navigation.Screens
import com.example.moviekmp.shareCore.presentation.navigation.ShowCategory
import com.example.moviekmp.shareCore.presentation.util.carousel.TrendingCarousel

@Composable
fun RouteTvShowScreen(navController: NavHostController, viewModel: ShowViewModel){

    val isDark = isSystemInDarkTheme()
    val trendingUiState by viewModel.trendingShows.collectAsStateWithLifecycle()
    val airingTodayShowsUiState by viewModel.airingTodayShows.collectAsStateWithLifecycle()
    val onAirShowsUiState by viewModel.onTheAirShows.collectAsStateWithLifecycle()
    val popularShowsUiState by viewModel.popularShows.collectAsStateWithLifecycle()
    val topRatedShowsUiState by viewModel.topRatedShows.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TrendingCarousel(state = trendingUiState)
        ShowsEndlessLazyRowSection(
            title = "Airing Today",
            isDark = isDark,
            shows = airingTodayShowsUiState.shows,
            isLoading = airingTodayShowsUiState.isLoading,
            hasMoreData = airingTodayShowsUiState.hasMoreData,
            loadMore = {
                viewModel.getAiringTodayShows()
            },
            onSeeAll = {
                navController.navigate(Screens.CategoryShows.createRoute(ShowCategory.AIRING_TODAY.apiValue))
            },
            onMovieClick = { movie ->}
        )
        ShowsEndlessLazyRowSection(
            title = "On The Air",
            isDark = isDark,
            shows = onAirShowsUiState.shows,
            isLoading = onAirShowsUiState.isLoading,
            hasMoreData = onAirShowsUiState.hasMoreData,
            loadMore = {
                viewModel.getOnTheAirShows()
            },
            onSeeAll = {
                navController.navigate(Screens.CategoryShows.createRoute(ShowCategory.ON_THE_AIR.apiValue))
            },
            onMovieClick = { movie ->}
        )
        ShowsEndlessLazyRowSection(
            title = "Popular",
            isDark = isDark,
            shows = popularShowsUiState.shows,
            isLoading = popularShowsUiState.isLoading,
            hasMoreData = popularShowsUiState.hasMoreData,
            loadMore = {
                viewModel.getPopularShows()
            },
            onSeeAll = {
                navController.navigate(Screens.CategoryShows.createRoute(ShowCategory.POPULAR.apiValue))
            },
            onMovieClick = { movie ->}
        )
        ShowsEndlessLazyRowSection(
            title = "Top Rated",
            isDark = isDark,
            shows = topRatedShowsUiState.shows,
            isLoading = topRatedShowsUiState.isLoading,
            hasMoreData = topRatedShowsUiState.hasMoreData,
            loadMore = {
                viewModel.getTopRatedShows()
            },
            onSeeAll = {
                navController.navigate(Screens.CategoryShows.createRoute(ShowCategory.TOP_RATED.apiValue))
            },
            onMovieClick = { movie ->}
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun Shows(
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
    )
}