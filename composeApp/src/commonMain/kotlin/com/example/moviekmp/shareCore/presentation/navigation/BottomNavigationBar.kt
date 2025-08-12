package com.example.moviekmp.shareCore.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviekmp.shareCore.presentation.navigation.BottomNavigationItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavigationBar(navController: NavHostController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
            NavigationBarItem(
                selected = navigationItem.route == currentDestination,
                label = {
                    Text(navigationItem.label)
                },
                icon = {
                    Image(
                        painter = painterResource(navigationItem.icon),
                        contentDescription = navigationItem.icon.toString(),
                        contentScale = ContentScale.Crop,
                    )
                },
                onClick = {
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}