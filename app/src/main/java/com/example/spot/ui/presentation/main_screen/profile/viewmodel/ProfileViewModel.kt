package com.example.spot.ui.presentation.main_screen.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 1. Importe o novo repositório
import com.example.spot.data.dtos.auth.UserPreferencesRepository
import com.example.spot.ui.presentation.main_screen.profile.model.InfoData
import com.example.spot.ui.presentation.main_screen.profile.model.ProfileState
import com.example.spot.ui.presentation.main_screen.profile.model.ProgressData
import com.example.spot.ui.presentation.main_screen.profile.model.StatsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = _state.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _isCheckingLogin = MutableStateFlow(true)
    val isCheckingLogin = _isCheckingLogin.asStateFlow()

    init {
        viewModelScope.launch {
            userPrefs.token.collect { token ->
                val logged = token.isNotEmpty()
                _isLoggedIn.value = logged
                _isCheckingLogin.value = false

                if (logged && _state.value is ProfileState.Loading) {
                    _state.update { ProfileState.Loading }

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
                } else if (!logged) {
                    _state.value = ProfileState.Loading
                }
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

    fun logout() {
        viewModelScope.launch {
            userPrefs.clearToken()
            _isLoggedIn.value = false
            _state.value = ProfileState.Loading
        }
    }
}