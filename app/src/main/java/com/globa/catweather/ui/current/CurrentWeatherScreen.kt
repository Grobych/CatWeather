package com.globa.catweather.ui.current

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.globa.catweather.R
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
                start = Offset(0.0f, 0.0f),
                end = Offset(0.0f, 1000f),
                colors = listOf(
                    Color(0xFF81BDF5),
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
                text = stringResource(id = R.string.feels_like, state.feelsLikeTemperature),
                Modifier.fillMaxWidth(0.45f), //will fix this
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
            Row() {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_cloud),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = state.weatherState,
                    style = DefaultWhiteText
                )
            }

            Row(Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_wind),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = stringResource(id = R.string.wind_template, state.windSpeed),
                    style = DefaultWhiteText
                )
            }

            Row {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_umbrella),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = stringResource(id = R.string.humidity_template, state.humidity),
                    style = DefaultWhiteText
                )
            }




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

