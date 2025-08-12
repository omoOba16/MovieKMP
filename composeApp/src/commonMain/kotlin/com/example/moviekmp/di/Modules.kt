package com.example.moviekmp.di

import com.example.moviekmp.core.data.HttpClientFactory
import com.example.moviekmp.core.data.repository.MovieRepositoryImp
import com.example.moviekmp.core.data.repository.ShowRepositoryImp
import com.example.moviekmp.data.network.movie.KtorRemoteMovieDataSource
import com.example.moviekmp.data.network.movie.RemoteMovieDataSource
import com.example.moviekmp.data.network.show.KtorRemoteShowDataSource
import com.example.moviekmp.data.network.show.RemoteShowDataSource
import com.example.moviekmp.data.repository.MovieRepository
import com.example.moviekmp.data.repository.ShowRepository
import com.example.moviekmp.presentation.movies.viewmodels.CategoryMoviesViewModel
import com.example.moviekmp.presentation.movies.viewmodels.MovieViewModel
import com.example.moviekmp.presentation.shows.viewmodel.CategoryShowsViewModel
import com.example.moviekmp.presentation.shows.viewmodel.ShowViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteMovieDataSource).bind<RemoteMovieDataSource>()
    singleOf(::KtorRemoteShowDataSource).bind<RemoteShowDataSource>()
    singleOf(::MovieRepositoryImp).bind<MovieRepository>()
    singleOf(::ShowRepositoryImp).bind<ShowRepository>()
    viewModelOf(::MovieViewModel)
    viewModelOf(::ShowViewModel)
    viewModelOf(::CategoryMoviesViewModel)
    viewModelOf(::CategoryShowsViewModel)
}