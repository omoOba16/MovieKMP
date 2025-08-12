package com.example.moviekmp.presentation.shows

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.moviekmp.presentation.shows.viewmodel.CategoryShowsViewModel
import com.example.moviekmp.shareCore.presentation.PosterCellUi
import com.example.moviekmp.shareCore.presentation.util.inifinteScrolling.EndlessLazyGridScrollListener
import moviekmp.composeapp.generated.resources.Res
import moviekmp.composeapp.generated.resources.ic_left_arrow
import moviekmp.composeapp.generated.resources.ic_right_arrow
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryShowsScreen(
    navController: NavController,
    viewModel: CategoryShowsViewModel
){
    val isDark = isSystemInDarkTheme()
    val showsUiState by viewModel.allShows.collectAsStateWithLifecycle()
    val lazyGridState = rememberLazyGridState()

    EndlessLazyGridScrollListener(
        lazyGridState = lazyGridState,
        isLoading = showsUiState.isLoading,
        hasMoreData = showsUiState.hasMoreData,
        loadMoreItems = {
            viewModel.getShows()
        },
        visibleItemsThreshold = 6
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(Res.drawable.ic_left_arrow),
                            contentDescription = "Back"
                        )
                    }
                }
                Text(
                    text = showsUiState.categoryName ?: "",
                    style = TextStyle(
                        color = if(isDark) Color.White else Color.Black,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight(700)
                    )
                )
            }
        }
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = showsUiState.shows){ item ->
                PosterCellUi(
                    imagePath = item.posterPath ?: "",
                    title = item.title ?: "",
                    onClick = { },
                    modifier = Modifier.aspectRatio(2f / 3f)
                )
            }
            if(showsUiState.isLoading && showsUiState.shows.isEmpty()){
                items(count = 12) {
                    PosterCellUi(
                        imagePath = "",
                        title = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
            }

            if(showsUiState.isLoading && showsUiState.shows.isNotEmpty()){
                items(count = 12) {
                    PosterCellUi(
                        imagePath = "",
                        title = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
            }
        }
    }
}