package com.example.moviekmp.shareCore.presentation.util.carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.moviekmp.core.AppLogger
import com.example.moviekmp.shareCore.presentation.CarouselState
import com.example.moviekmp.shareCore.presentation.util.shimmer.ShimmerAnimateBrush
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TrendingCarousel(
    modifier: Modifier = Modifier,
    state: CarouselState,
){
    Box(
        modifier = modifier
    ){
        if(state.isLoading && state.carousels.isEmpty()){

            val shimmerAnimateBrush = ShimmerAnimateBrush()

            Spacer(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .height(500.dp)
                    .background(shimmerAnimateBrush, RoundedCornerShape(10.dp))
            )

        } else {

            val posters = state.carousels
            val pagerState = rememberPagerState(pageCount = {
                if(posters.isNotEmpty()) posters.size else 0
            })
            HorizontalPager(
                state = pagerState,
                pageSpacing = 12.dp,
                contentPadding = PaddingValues(horizontal = 30.dp)
            ) { page ->

                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalPlatformContext.current)
                        .data(posters[page].posterPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .border(1.dp, color = Color(0xFFF2F2F2), RoundedCornerShape(10.dp))
                        //.clip(RoundedCornerShape(10.dp))
                        .shadow(10.dp, RoundedCornerShape(10.dp), true),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}