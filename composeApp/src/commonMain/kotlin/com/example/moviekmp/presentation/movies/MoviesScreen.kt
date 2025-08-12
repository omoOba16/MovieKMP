package com.example.moviekmp.presentation.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.moviekmp.presentation.movies.components.MoviesEndlessLazyRowSection
import com.example.moviekmp.presentation.movies.viewmodels.MovieViewModel
import com.example.moviekmp.shareCore.presentation.navigation.MovieCategory
import com.example.moviekmp.shareCore.presentation.navigation.Screens
import com.example.moviekmp.shareCore.presentation.util.carousel.TrendingCarousel
import com.example.moviekmp.shareCore.presentation.util.shimmer.ShimmerAnimateBrush
import com.example.moviekmp.shareCore.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_ORIGINAL
import moviekmp.composeapp.generated.resources.Res
import moviekmp.composeapp.generated.resources.ic_left_arrow
import moviekmp.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteMovieScreen(navController: NavHostController, viewModel: MovieViewModel){

    val isDark = isSystemInDarkTheme()
    val trendingUiState by viewModel.trendingMovies.collectAsStateWithLifecycle()
    val nowPlayingUiState by viewModel.nowPlayingMovies.collectAsStateWithLifecycle()
    val popularUiState by viewModel.popularMovies.collectAsStateWithLifecycle()
    val topRatedUiState by viewModel.topRatedMovies.collectAsStateWithLifecycle()
    val upComingUiState by viewModel.upComingMovies.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ){
            Spacer(modifier = Modifier.height(64.dp))
            TrendingCarousel(state = trendingUiState)
            MoviesEndlessLazyRowSection(
                title = "Now Playing",
                isDark = isDark,
                movies = nowPlayingUiState.movies,
                isLoading = nowPlayingUiState.isLoading,
                hasMoreData = nowPlayingUiState.hasMoreData,
                loadMore = {
                    viewModel.getNowPlayingMovies()
                },
                onSeeAll = {
                    navController.navigate(Screens.CategoryMovies.createRoute(MovieCategory.NOW_PLAYING.apiValue))
                },
                onMovieClick = { movie ->}
            )
            MoviesEndlessLazyRowSection(
                title = "Popular",
                isDark = isDark,
                movies = popularUiState.movies,
                isLoading = popularUiState.isLoading,
                hasMoreData = popularUiState.hasMoreData,
                loadMore = {
                    viewModel.getNowPlayingMovies()
                },
                onSeeAll = {
                    navController.navigate(Screens.CategoryMovies.createRoute(MovieCategory.POPULAR.apiValue))
                },
                onMovieClick = { movie ->}
            )
            MoviesEndlessLazyRowSection(
                title = "Top Rated",
                isDark = isDark,
                movies = topRatedUiState.movies,
                isLoading = topRatedUiState.isLoading,
                hasMoreData = topRatedUiState.hasMoreData,
                loadMore = {
                    viewModel.getTopRatedMovies()
                },
                onSeeAll = {
                    navController.navigate(Screens.CategoryMovies.createRoute(MovieCategory.TOP_RATED.apiValue))
                },
                onMovieClick = { movie ->}
            )
            MoviesEndlessLazyRowSection(
                title = "Upcoming",
                isDark = isDark,
                movies = upComingUiState.movies,
                isLoading = upComingUiState.isLoading,
                hasMoreData = upComingUiState.hasMoreData,
                loadMore = {
                    viewModel.getUpcomingMovies()
                },
                onSeeAll = {
                    navController.navigate(Screens.CategoryMovies.createRoute(MovieCategory.UPCOMING.apiValue))
                },
                onMovieClick = { movie ->}
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color.Black.copy(alpha = 0.7f))
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.weight(1f))
            /*IconButton(onClick = {  }) {
                    Image(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = "Search",
                        modifier = Modifier.padding(12.dp)
                    )
                }*/
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description"
                )
            }
        }


    }

    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ){
        Spacer(modifier = Modifier.height(60.dp))
        TrendingCarousel(state = trendingUiState)
        MoviesEndlessLazyRowSection(
            title = "Now Playing",
            isDark = isDark,
            movies = nowPlayingUiState.movies,
            isLoading = nowPlayingUiState.isLoading,
            hasMoreData = nowPlayingUiState.hasMoreData,
            loadMore = {
                viewModel.getNowPlayingMovies()
            },
            onSeeAll = {
                navController.navigate(Screens.CategoryMovies.createRoute(MovieCategory.NOW_PLAYING.apiValue))
            },
            onMovieClick = { movie ->}
        )
        MoviesEndlessLazyRowSection(
            title = "Popular",
            isDark = isDark,
            movies = popularUiState.movies,
            isLoading = popularUiState.isLoading,
            hasMoreData = popularUiState.hasMoreData,
            loadMore = {
                viewModel.getNowPlayingMovies()
            },
            onSeeAll = {
                navController.navigate(Screens.CategoryMovies.createRoute(MovieCategory.POPULAR.apiValue))
            },
            onMovieClick = { movie ->}
        )
        MoviesEndlessLazyRowSection(
            title = "Top Rated",
            isDark = isDark,
            movies = topRatedUiState.movies,
            isLoading = topRatedUiState.isLoading,
            hasMoreData = topRatedUiState.hasMoreData,
            loadMore = {
                viewModel.getTopRatedMovies()
            },
            onSeeAll = {
                navController.navigate(Screens.CategoryMovies.createRoute(MovieCategory.TOP_RATED.apiValue))
            },
            onMovieClick = { movie ->}
        )
        MoviesEndlessLazyRowSection(
            title = "Upcoming",
            isDark = isDark,
            movies = upComingUiState.movies,
            isLoading = upComingUiState.isLoading,
            hasMoreData = upComingUiState.hasMoreData,
            loadMore = {
                viewModel.getUpcomingMovies()
            },
            onSeeAll = {
                navController.navigate(Screens.CategoryMovies.createRoute(MovieCategory.UPCOMING.apiValue))
            },
            onMovieClick = { movie ->}
        )
        Spacer(modifier = Modifier.height(30.dp))
    }*/
}
