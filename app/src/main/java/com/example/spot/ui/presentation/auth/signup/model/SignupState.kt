    package com.example.spot.ui.presentation.auth.signup.model

    sealed class SignupState {
        data object Idle : SignupState()
        data object Loading : SignupState()
        data class Error(val message: String): SignupState()
        data object Success : SignupState()
    }