package com.globa.catweather.ui.constants

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.globa.catweather.R

val manropeFamily = FontFamily(
    Font(R.font.manrope_light, FontWeight.Light),
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_bold, FontWeight.Bold)
)

val DefaultWhiteText = TextStyle(
    color = Color.White,
    fontSize = 14.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.Normal
)

val BigWhiteText = TextStyle(
    color = Color.White,
    fontSize = 64.sp,
    fontFamily = manropeFamily,
    fontWeight = FontWeight.Normal
)

