package com.globa.catweather.ui.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
        -15,
        -20,
        5,
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
    Box {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.plate),
            contentDescription = null
        )
        Row(modifier = Modifier
            .height(Dp(280f))
            .width(Dp(360f))
        ) {
            Column(
                Modifier
                    .padding(
                        start = 45.dp,
                        top = 90.dp
                    )
                    .height(140.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.temperature_template, state.temperature),
                    style = BigWhiteText)
                Text(
                    text = stringResource(id = R.string.feels_like, state.feelsLikeTemperature),
                    Modifier.align(CenterHorizontally),
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
                Row(
                    Modifier.height(20.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_cloud),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .align(CenterVertically)
                    )
                    Text(
                        text = state.weatherState,
                        style = DefaultWhiteText
                    )
                }

                Row(
                    Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .height(20.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_wind),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .align(CenterVertically)
                    )
                    Text(
                        text = stringResource(id = R.string.wind_template, state.windSpeed),
                        style = DefaultWhiteText
                    )
                }

                Row(
                    Modifier.height(20.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_umbrella),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .align(CenterVertically)
                    )
                    Text(
                        text = stringResource(id = R.string.humidity_template, state.humidity),
                        style = DefaultWhiteText
                    )
                }
            }
        }
    }
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
        -15,
        -20,
        5,
        20,
        0
    )
    MainInfoBlock(state)
}

