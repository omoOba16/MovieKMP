package com.example.moviekmp.shareCore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moviekmp.presentation.movies.CategoryMoviesScreen
import com.example.moviekmp.presentation.movies.RouteMovieScreen
import com.example.moviekmp.presentation.movies.viewmodels.CategoryMoviesViewModel
import com.example.moviekmp.presentation.movies.viewmodels.MovieViewModel
import com.example.moviekmp.presentation.shows.CategoryShowsScreen
import com.example.moviekmp.presentation.shows.RouteTvShowScreen
import com.example.moviekmp.presentation.shows.viewmodel.CategoryShowsViewModel
import com.example.moviekmp.presentation.shows.viewmodel.ShowViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Movies.route) {

        // Define your screens here

        // Movies Screen
        composable(Screens.Movies.route) {
            val viewModel: MovieViewModel = koinViewModel()
            RouteMovieScreen(navController, viewModel)
        }

        composable(
            route = Screens.CategoryMovies.route,
            arguments = listOf(
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) {
            val viewModel: CategoryMoviesViewModel = koinViewModel()
            CategoryMoviesScreen(navController, viewModel)
        }

        // Shows Screen
        composable(Screens.Shows.route) {
            val viewModel: ShowViewModel = koinViewModel()
            RouteTvShowScreen(navController, viewModel)
        }

        composable(
            route = Screens.CategoryShows.route,
            arguments = listOf(
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) {
            val viewModel: CategoryShowsViewModel = koinViewModel()
            CategoryShowsScreen(navController, viewModel)
        }
    }
}