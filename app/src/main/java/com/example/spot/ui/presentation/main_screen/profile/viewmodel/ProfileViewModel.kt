package com.example.spot.ui.presentation.main_screen.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.ui.presentation.main_screen.profile.model.ProfileState
import com.example.spot.ui.presentation.main_screen.profile.model.InfoData
import com.example.spot.ui.presentation.main_screen.profile.model.ProgressData
import com.example.spot.ui.presentation.main_screen.profile.model.StatsData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000L)

            val profileInfo = fetchProfileInfo()
            val profileProgress = fetchProfileProgress()
            val profileStats = fetchProfileStats()

            _state.update {
                ProfileState.Success(
                    infoData = profileInfo,
                    progressData = profileProgress,
                    statsData = profileStats
                )
            }
        }
    }

    private fun fetchProfileInfo(): InfoData {
        return InfoData(
            name = "Daniel Alves Almeida",
            username = "@danielalmeidafr"
        )
    }

    private fun fetchProfileProgress(): ProgressData {
        return ProgressData(
            currentVisits = 4,
            goalVisits = 5,
            rewardText = "10% OFF no próximo serviço"
        )
    }

    private fun fetchProfileStats(): StatsData {
        return StatsData(
            reviews = 12,
            schedules = 4,
            favorites = 5
        )
    }
}