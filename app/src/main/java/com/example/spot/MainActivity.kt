package com.example.spot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.home.HomeDestination
import com.example.spot.ui.presentation.home.homeScreen
import com.example.spot.ui.presentation.login.LoginDestination
import com.example.spot.ui.presentation.login.LoginScreen
import com.example.spot.ui.presentation.login.loginScreen
import com.example.spot.ui.presentation.signup.SignupDestination
import com.example.spot.ui.presentation.signup.SignupScreen
import com.example.spot.ui.presentation.signup.signupScreen
import com.example.spot.ui.theme.SpotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = LoginDestination,
                modifier = Modifier.fillMaxSize()
            ) {
                loginScreen(
                    onNavigateToHome = { navController.navigate(HomeDestination) },
                    onNavigateToSignup = { navController.navigate(SignupDestination) }
                )

                signupScreen(
                    onNavigateToHome = { navController.navigate(HomeDestination) },
                    onNavigateToLogin = { navController.navigate(LoginDestination) }
                )

                homeScreen()
            }

        }
    }
}

