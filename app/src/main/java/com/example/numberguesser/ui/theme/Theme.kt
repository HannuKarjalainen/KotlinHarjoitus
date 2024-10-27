package com.example.numberguesser.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Green30,
    onPrimary = Color.White,
    secondary = Color.Blue,
    onSecondary = Color.White,
    tertiary = Color.Gray,
    onTertiary = Color.Black,
    background = Black80,
    onBackground = Color.Gray,
    surface = Black40,
    onSurface = Color.Gray
)

private val LightColorScheme = lightColorScheme(
    primary = Green30,
    onPrimary = Color.White,
    secondary = Color.Blue,
    onSecondary = Color.White,
    tertiary = Color.Gray,
    onTertiary = Color.Black,
    background = LightGray,
    onBackground = Black80,
    surface = Color.Gray,
    onSurface = Red40
)

@Composable
fun NumberguesserTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
