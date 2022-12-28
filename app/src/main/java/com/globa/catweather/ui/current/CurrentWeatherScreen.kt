package com.globa.catweather.ui.current

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.catweather.R
import com.globa.catweather.ui.ScreenStateType

@Composable
fun CurrentWeatherScreen() {

}

@Composable
fun FooterBlock() {

}

@Composable
fun LocationAndDateBlock() {

}

@Preview
@Composable
fun CurrentWeatherScreenPreview() {
    val mainInfoState = MainInfoBlockState(
        ScreenStateType.DONE,
        "Overcast",
        -15,
        -20,
        5,
        20
    )
    val mainImageState = MainImageBlockState(
        ScreenStateType.DONE,
        R.drawable.winter_cat_preview
    )
    Box(modifier = Modifier.size(
        width = 360.dp,
        height = 740.dp
    )) {
        Box(modifier = Modifier
            .padding(top = 240.dp)
        ){
            MainImageBlock(state = mainImageState)
        }
        MainInfoBlock(state = mainInfoState)
    }
}

