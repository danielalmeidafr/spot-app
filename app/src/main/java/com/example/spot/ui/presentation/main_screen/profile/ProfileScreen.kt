package com.example.spot.ui.presentation.main_screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.ui.presentation.main_screen.profile.components.ProgressBar
import com.example.spot.ui.presentation.main_screen.profile.components.sections.HeaderSection
import com.example.spot.ui.presentation.main_screen.profile.components.sections.InfoSection
import com.example.spot.ui.presentation.main_screen.profile.components.sections.OptionsSection
import com.example.spot.ui.presentation.main_screen.profile.components.sections.StatsSection
import com.example.spot.ui.presentation.main_screen.profile.components.skeletons.InfoSectionSkeleton
import com.example.spot.ui.presentation.main_screen.profile.components.skeletons.ProgressBarSkeleton
import com.example.spot.ui.presentation.main_screen.profile.components.skeletons.StatsSectionSkeleton
import com.example.spot.ui.presentation.main_screen.profile.model.ProfileState
import com.example.spot.ui.presentation.main_screen.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: () -> Unit,
    innerPadding: PaddingValues = PaddingValues(),
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val viewModel = viewModel<ProfileViewModel>()
    val state by viewModel.state.collectAsState()

    val isLogged = true

    when (val state = state) {
        ProfileState.Loading -> {
            if (isLogged) {
                ProfileLoggedOutScreen(
                    onNavigateToSignIn = onNavigateToSignIn,
                    innerPadding = innerPadding
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item { HeaderSection(isDarkTheme, onThemeToggle) }

                    item { InfoSectionSkeleton() }

                    item { ProgressBarSkeleton() }

                    item { StatsSectionSkeleton() }

                    item {
                        OptionsSection(
                            onLogout = { /* TODO: implementar logout */ }
                        )
                    }
                }
            }
        }

        is ProfileState.Success -> {
            if (isLogged) {
                ProfileLoggedOutScreen(
                    onNavigateToSignIn = onNavigateToSignIn,
                    innerPadding = innerPadding
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item { HeaderSection(isDarkTheme, onThemeToggle) }

                    item { InfoSection(infoData = state.infoData) }

                    item { ProgressBar(progressData = state.progressData) }

                    item { StatsSection(statsData = state.statsData) }

                    item {
                        OptionsSection(
                            onLogout = { /* TODO: implementar logout */ }
                        )
                    }
                }
            }
        }
    }
}