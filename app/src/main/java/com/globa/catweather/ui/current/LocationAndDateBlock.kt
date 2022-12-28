package com.globa.catweather.ui.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.catweather.R

import com.globa.catweather.ui.ScreenStateType
import com.globa.catweather.ui.constants.BoldBlackText
import com.globa.catweather.ui.constants.LightBlackText

@Composable
fun LocationAndDateBlock(state: LocationAndDateBlockState) {
    when (state.state) {
        ScreenStateType.LOADING -> LocationAndDateBlockLoading()
        ScreenStateType.ERROR -> LocationAndDateBlockError()
        ScreenStateType.DONE -> LocationAndDateBlockDone(state)
    }
}

@Composable
fun LocationAndDateBlockDone(state: LocationAndDateBlockState) {
    Box {
        Image(painter = painterResource(id = R.drawable.paw), contentDescription = null)
        Text(
            text = buildAnnotatedString {
                withStyle(BoldBlackText.toSpanStyle()) {
                    append(state.location + " | ")
                }
                withStyle(LightBlackText.toSpanStyle()){
                    append(state.dayOfWeek + ", " + state.dayOfMonth)
                }
            },
            modifier = Modifier
                .padding(30.dp)
        )
    }
    //TODO: choose one
//    ConstraintLayout {
//        val (
//            pawImage,
//            textField
//        ) = createRefs()
//        Image(
//            painter = painterResource(id = R.drawable.paw),
//            contentDescription = null,
//            modifier = Modifier.constrainAs(pawImage){
//                top.linkTo(parent.top)
//                bottom.linkTo(parent.bottom)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            }
//        )
//        Text(
//            text = buildAnnotatedString {
//                withStyle(BoldBlackText.toSpanStyle()) {
//                    append(state.location + " | ")
//                }
//                withStyle(LightBlackText.toSpanStyle()){
//                    append(state.dayOfWeek + ", " + state.dayOfMonth)
//                }
//            },
//            Modifier
//                .constrainAs(textField){
//                    top.linkTo(pawImage.top)
//                    bottom.linkTo(pawImage.bottom)
//                    start.linkTo(pawImage.start)
//                    end.linkTo(pawImage.end)
//                }
//                .padding(end = 20.dp)
//        )
//    }
}

@Composable
fun LocationAndDateBlockError() {
    TODO("Not yet implemented")
}

@Composable
fun LocationAndDateBlockLoading() {
    TODO("Not yet implemented")
}

@Composable
@Preview
fun LocationAndDateBlockPreview() {
    val state = LocationAndDateBlockState(
        state = ScreenStateType.DONE,
        location = "Minsk",
        dayOfWeek = "Thursday",
        dayOfMonth = 15
    )
    LocationAndDateBlock(state = state)
}
