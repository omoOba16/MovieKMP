package com.example.moviekmp.shareCore.presentation.navigation

import moviekmp.composeapp.generated.resources.Res
import moviekmp.composeapp.generated.resources.ic_movie
import moviekmp.composeapp.generated.resources.ic_tv
import org.jetbrains.compose.resources.DrawableResource

data class BottomNavigationItem(
    val label : String = "",
    val icon : DrawableResource = Res.drawable.ic_movie,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Movies",
                icon =  Res.drawable.ic_movie,
                route = Screens.Movies.route
            ),
            BottomNavigationItem(
                label = "Tv Shows",
                icon = Res.drawable.ic_tv,
                route = Screens.Shows.route
            )
        )
    }
}