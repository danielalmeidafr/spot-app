package com.example.spot.ui.presentation.auth.model

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
    data object Success : AuthState()
}