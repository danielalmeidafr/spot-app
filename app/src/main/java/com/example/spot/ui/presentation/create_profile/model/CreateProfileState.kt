package com.example.spot.ui.presentation.create_profile.model

sealed class CreateProfileState {
        data object Idle : CreateProfileState()
        data object Loading : CreateProfileState()
        data class Error(val message: String): CreateProfileState()
        data object Success : CreateProfileState()
}