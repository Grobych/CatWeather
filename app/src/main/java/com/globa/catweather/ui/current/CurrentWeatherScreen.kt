package com.globa.catweather.ui.current

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.globa.catweather.ui.ScreenStateType

@Composable
fun CurrentWeatherScreen() {

}


@Composable
fun ImageBlock() {

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
    val state = MainInfoBlockState(
        ScreenStateType.DONE,
        "Overcast",
        -15,
        -20,
        5,
        20
    )
    MainInfoBlock(state)
    ImageBlock()
}

