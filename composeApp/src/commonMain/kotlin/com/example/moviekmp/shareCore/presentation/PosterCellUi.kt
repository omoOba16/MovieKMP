package com.example.moviekmp.shareCore.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.compose.LocalPlatformContext
import coil3.request.crossfade
import com.example.moviekmp.shareCore.presentation.util.shimmer.ShimmerAnimateBrush
import com.example.moviekmp.shareCore.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_W342
import com.example.moviekmp.shareCore.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_W500
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PosterCellUi(
    modifier: Modifier = Modifier,
    imagePath: String = "",
    title: String = "",
    onClick: (() -> Unit?) = {},
    isSkeleton: Boolean = false
){
    val shimmerAnimateBrush = ShimmerAnimateBrush()

    Box(modifier = modifier) {
        if(isSkeleton){
            Spacer(modifier = Modifier
                .width(150.dp)
                .aspectRatio(2f / 3f)
                .background(shimmerAnimateBrush, RoundedCornerShape(10.dp))
            )
        } else {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalPlatformContext.current)
                    .data("${MOVIE_IMAGE_POSTER_SIZE_W342}${imagePath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "$title Poster",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onClick() },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
@Preview
fun PosterCellUiPreview(){
    PosterCellUi(
        imagePath = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
        title = "Title"
    )
}