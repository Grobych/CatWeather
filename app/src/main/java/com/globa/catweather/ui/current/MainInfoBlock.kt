package com.globa.catweather.ui.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.catweather.R
import com.globa.catweather.ui.ScreenStateType
import com.globa.catweather.ui.constants.BigWhiteText
import com.globa.catweather.ui.constants.DefaultWhiteText

@Composable
fun MainInfoBlock(
    state: MainInfoBlockState
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
    state: MainInfoBlockState
) {
    Box(
        Modifier
            .requiredSize(360.dp, 280.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.plate),
            null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Row {
            Column(
                Modifier
                    .padding(
                        start = 45.dp,
                        top = 110.dp
                    )
                    .height(140.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.temperature_template, state.temperature),
                    style = BigWhiteText
                )
                Text(
                    text = stringResource(id = R.string.feels_like, state.feelsLikeTemperature),
                    Modifier.align(Alignment.CenterHorizontally),
                    style = DefaultWhiteText
                )
            }
            Column(
                Modifier
                    .padding(
                        start = 34.dp,
                        top = 110.dp
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
                            .align(Alignment.CenterVertically)
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
                            .align(Alignment.CenterVertically)
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
                            .align(Alignment.CenterVertically)
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

@Preview
@Composable
fun CMainInfoBlockPreview() {
    val state = MainInfoBlockState(
        ScreenStateType.DONE,
        "Overcast",
        -15,
        -20,
        5,
        20
    )
    MainInfoBlock(state)
}