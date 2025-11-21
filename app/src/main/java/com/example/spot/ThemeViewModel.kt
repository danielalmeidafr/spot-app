package com.example.spot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ThemeViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean?> = userPreferencesRepository.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun toggleTheme() {
        viewModelScope.launch {
            val currentMode = isDarkTheme.value ?: false
            userPreferencesRepository.saveThemePreference(!currentMode)
        }
    }
}