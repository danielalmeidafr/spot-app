package com.example.spot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spot.core.navigation.AppNavHost
import com.example.spot.core.theme.SpotTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeViewModel = koinViewModel<ThemeViewModel>()

            val isDarkTheme by themeViewModel.isDarkTheme.collectAsStateWithLifecycle()

            splashScreen.setKeepOnScreenCondition {
                isDarkTheme == null
            }

            if (isDarkTheme != null) {
                SpotTheme(darkTheme = isDarkTheme!!) {
                    AppNavHost(
                        isDarkTheme = isDarkTheme!!,
                        onThemeToggle = { themeViewModel.toggleTheme() }
                    )
                }
            }
        }
    }
}