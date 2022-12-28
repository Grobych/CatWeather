package com.globa.catweather.ui.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.catweather.R
import com.globa.catweather.ui.ScreenStateType
import com.globa.catweather.ui.constants.BoldBlackText
import com.globa.catweather.ui.constants.LittleBlueText

@Composable
fun MainScreenFooterBlock(state: MainScreenFooterBlockState) {
    when (state.state) {
        ScreenStateType.LOADING -> FooterBlockLoading()
        ScreenStateType.ERROR -> FooterBlockError()
        ScreenStateType.DONE -> FooterBlockDone(state)
    }
}

@Composable
fun FooterBlockLoading() {
    TODO("Not yet implemented")
}

@Composable
fun FooterBlockError() {
    TODO("Not yet implemented")
}

@Composable
fun FooterBlockDone(state: MainScreenFooterBlockState) {

    Box {
        Image(
            painter = painterResource(id = R.drawable.footer_plate),
            contentDescription = null,
            Modifier
                .fillMaxWidth()
                .shadow(
                elevation = 4.dp,
                shape = RectangleShape,
                clip = true,
                spotColor = Color(0xFF001549)
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.map.forEach { (time, temperature) ->
                Column(
                    verticalArrangement = SpaceEvenly,
                    horizontalAlignment = CenterHorizontally
                ) {
                    Text(
                        text = time,
                        style = LittleBlueText
                    )
                    Text(
                        text = stringResource(id = R.string.temperature_template, temperature),
                        Modifier.padding(top = 10.dp),
                        style = BoldBlackText
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun FooterBlockDonePreview() {
    val state = MainScreenFooterBlockState(
        state = ScreenStateType.DONE,
        map = mapOf(
            "00:00" to -26,
            "04:00" to -16,
            "08:00" to -15,
            "12:00" to -15,
            "16:00" to -13,
            "20:00" to -13
        )
    )
    FooterBlockDone(state = state)
}
