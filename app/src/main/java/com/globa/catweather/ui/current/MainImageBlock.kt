package com.globa.catweather.ui.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
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
        modifier = Modifier.fillMaxSize())
}

@Composable
fun MainImageBlockError() {
    TODO("Not yet implemented")
}

@Composable
fun MainImageBlockLoading() {
    TODO("Not yet implemented")
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
