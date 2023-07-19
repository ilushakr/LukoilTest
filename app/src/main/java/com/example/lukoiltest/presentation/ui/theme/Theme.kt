package com.example.lukoiltest.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple80,
    secondary = PurpleGrey80,
    primaryVariant = Pink80
)

private val LightColorPalette = lightColors(
    primary = Purple40,
    secondary = PurpleGrey40,
    primaryVariant = Pink40

)

@Composable
fun LukoilTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}
