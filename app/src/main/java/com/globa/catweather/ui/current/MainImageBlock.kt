package com.globa.catweather.ui.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.catweather.R
import com.globa.catweather.ui.ScreenStateType

@Composable
fun MainImageBlock(state: MainImageBlockState) {
    when (state.state) {
        ScreenStateType.LOADING -> MainImageBlockLoading()
        ScreenStateType.ERROR -> MainImageBlockError()
        ScreenStateType.DONE -> MainImageBlockDone(state.image)
    }
}

@Composable
fun MainImageBlockDone(image: Int) {
    Image(
        bitmap = ImageBitmap.imageResource(id = image),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MainImageBlockError() {
    TODO("Not yet implemented")
}

@Composable
fun MainImageBlockLoading() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.cat_placeholder),
            contentDescription = null,
            Modifier.size(
                width = 100.dp,
                height = 100.dp
            )
            .align(Alignment.Center)
        )
    }
}

@Composable
@Preview
fun MainImageBlockPreview() {
    val state = MainImageBlockState(
        ScreenStateType.DONE,
        R.drawable.winter_cat_preview
    )
    MainImageBlock(state = state)
}

@Composable
@Preview
fun MainImageBlockLoadingPreview() {
    val state = MainImageBlockState(
        ScreenStateType.LOADING
    )
    MainImageBlock(state = state)
}
