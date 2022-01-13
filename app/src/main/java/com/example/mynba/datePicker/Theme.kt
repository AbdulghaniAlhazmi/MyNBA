package com.example.mynba.datePicker

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightPlateColors = lightColors(
    primary = Blue,
    primaryVariant = Grey,
    secondary = White
)


@Composable
fun DatePickerTimeLineTheme(
    content : @Composable () -> Unit
){
    val colors = LightPlateColors

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}