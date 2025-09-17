package com.example.spot.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Golden,
    onPrimary = White,

    secondary = Green,

    background = DarkGray,
    onBackground = White,

    surface = DarkGray2,
    onSurface = White,

    surfaceContainer = DarkGray3,

    outline = White04,
    outlineVariant = DarkGray4
)

private val LightColorScheme = lightColorScheme(
    primary = Golden,
    onPrimary = White,

    secondary = Green,

    background = WhiteBack,
    onBackground = Black,

    surface = WhiteVariant,
    onSurface = Black,

    surfaceContainer = White,

    outline = Black04,
    outlineVariant = WhiteVariant2
)

@Composable
fun SpotTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}