package com.example.spot.ui.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.dtos.auth.usecase.ErrorCause
import com.example.spot.data.dtos.auth.usecase.Result
import com.example.spot.data.dtos.auth.usecase.SignInUseCase
import com.example.spot.data.dtos.auth.usecase.SignUpUseCase
import com.example.spot.data.dtos.auth.usecase.confirm_code.ConfirmCodePasswordUseCase
import com.example.spot.data.dtos.auth.usecase.confirm_code.ConfirmCodeSignUpUseCase
import com.example.spot.data.dtos.auth.usecase.password.ForgotPasswordUseCase
import com.example.spot.data.dtos.auth.usecase.password.NewPasswordUseCase
import com.example.spot.ui.presentation.auth.model.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val newPasswordUseCase: NewPasswordUseCase,
    private val confirmCodePasswordUseCase: ConfirmCodePasswordUseCase,
    private val confirmCodeSignUpUseCase: ConfirmCodeSignUpUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state = _state.asStateFlow()

    fun onSignUpClicked(email: String, password: String) {
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            when (val result = signUpUseCase(email, password)) {
                is Result.Success -> _state.update { AuthState.Success }
                is Result.Error -> {
                    val errorMessage = when (val cause = result.cause) {
                        is ErrorCause.ValidationError -> cause.message
                        is ErrorCause.ApiError -> cause.message
                    }
                    _state.update { AuthState.Error(errorMessage) }
                }
            }
        }
    }

    fun onSignInClicked(email: String, password: String) {
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            when (val result = signInUseCase(email, password)) {
                is Result.Success -> _state.update { AuthState.Success }
                is Result.Error -> {
                    val errorMessage = when (val cause = result.cause) {
                        is ErrorCause.ValidationError -> cause.message
                        is ErrorCause.ApiError -> cause.message
                    }
                    _state.update { AuthState.Error(errorMessage) }
                }
            }
        }
    }

    fun onForgotPasswordClicked(email: String) {
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            when (val result = forgotPasswordUseCase(email)) {
                is Result.Success -> _state.update { AuthState.Success }
                is Result.Error -> {
                    val errorMessage = when (val cause = result.cause) {
                        is ErrorCause.ValidationError -> cause.message
                        is ErrorCause.ApiError -> cause.message
                    }
                    _state.update { AuthState.Error(errorMessage) }
                }
            }
        }
    }

    fun onConfirmCodePasswordClicked(email: String, code: String) {
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            when (val result = confirmCodePasswordUseCase(email, code)) {
                is Result.Success -> _state.update { AuthState.Success }
                is Result.Error -> {
                    val errorMessage = when (val cause = result.cause) {
                        is ErrorCause.ValidationError -> cause.message
                        is ErrorCause.ApiError -> cause.message
                    }
                    _state.update { AuthState.Error(errorMessage) }
                }
            }
        }
    }

    fun onConfirmCodeSignUpClicked(email: String, code: String, password: String) {
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            when (val result = confirmCodeSignUpUseCase(email, code, password)) {
                is Result.Success -> _state.update { AuthState.Success }
                is Result.Error -> {
                    val errorMessage = when (val cause = result.cause) {
                        is ErrorCause.ValidationError -> cause.message
                        is ErrorCause.ApiError -> cause.message
                    }
                    _state.update { AuthState.Error(errorMessage) }
                }
            }
        }
    }

    fun onNewPasswordClicked(email: String, code: String, newPassword: String) {
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            when (val result = newPasswordUseCase(email, code, newPassword)) {
                is Result.Success -> _state.update { AuthState.Success }
                is Result.Error -> {
                    val errorMessage = when (val cause = result.cause) {
                        is ErrorCause.ValidationError -> cause.message
                        is ErrorCause.ApiError -> cause.message
                    }
                    _state.update { AuthState.Error(errorMessage) }
                }
            }
        }
    }
}