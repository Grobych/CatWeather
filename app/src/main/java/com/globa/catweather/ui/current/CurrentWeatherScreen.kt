package com.globa.catweather.ui.current

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.globa.catweather.ui.ScreenStateType
import com.globa.catweather.ui.constants.BigWhiteText
import com.globa.catweather.ui.constants.DefaultWhiteText

@Composable
fun CurrentWeatherScreen() {
    val state = CurrentWeatherScreenState(
        ScreenStateType.DONE,
        "Overcast",
        -15f,
        -20f,
        5f,
        20,
        0
    )
    MainInfoBlock(state)
}

@Composable
fun MainInfoBlock(
    state: CurrentWeatherScreenState
) {
    when (state.state) {
        ScreenStateType.DONE -> MainInfoBlockDone(state)
        ScreenStateType.LOADING -> MainInfoBlockLoading()
        ScreenStateType.ERROR -> MainInfoBlockError()
    }
}

@Composable
fun MainInfoBlockError() {
//    TODO("Not yet implemented")
}

@Composable
fun MainInfoBlockLoading() {
//    TODO("Not yet implemented")
}

@Composable
fun MainInfoBlockDone(
    state: CurrentWeatherScreenState
) {
    Row(modifier = Modifier
        .height(Dp(280f))
        .width(Dp(360f))
        .background(
            Brush.linearGradient(
                start = Offset(0.25f, 0.5f),
                end = Offset(0.75f, 0.5f),
                colors = listOf(
                    Color(0xFF81BDF5),
                    Color(0xFF7DB5F6),
                    Color(0xFF6786F4)
                )
            )
        )
    ) {
        Column(
            Modifier.padding(
                start = 45.dp,
                top = 90.dp
            )
        ) {
            Text(
                text = state.temperature.toString(),
                style = BigWhiteText)
            Text(
                text = state.feelsLikeTemperature.toString(),
                textAlign = TextAlign.Center,
                style = DefaultWhiteText
            )
        }
        Column(
            Modifier
                .padding(
                    start = 34.dp,
                    top = 90.dp
                )
        ) {
            Text(
                text = state.weatherState,
                style = DefaultWhiteText
            )
            Text(
                text = state.windSpeed.toString(),
                Modifier.padding(top = 20.dp, bottom = 20.dp),
                style = DefaultWhiteText
            )
            Text(
                text = state.humidity.toString(),
                style = DefaultWhiteText
            )

        }

    }
    
//    Text(text = state.weatherState)
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
    val state = CurrentWeatherScreenState(
        ScreenStateType.DONE,
        "Overcast",
        -15f,
        -20f,
        5f,
        20,
        0
    )
    MainInfoBlock(state)
}

