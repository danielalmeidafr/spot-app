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

    background = DarkGray,
    onBackground = White,

    surface = DarkGray2,
    onSurface = White,

    onSurfaceVariant = White50,
    outline = White30,

    surfaceContainer = DarkGray3
)

private val LightColorScheme = lightColorScheme(
    primary = Golden,
    onPrimary = White,

    background = WhiteVariant,
    onBackground = MediumGray,

    surface = WhiteVariant2,
    onSurface = MediumGray,

    onSurfaceVariant = MediumGray50,
    outline = MediumGray30,

    surfaceContainer = White
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