package com.example.spot.ui.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.dtos.auth.AuthRepository
import com.example.spot.data.dtos.auth.TokenManagerImpl
import com.example.spot.data.dtos.auth.login.SignInRequest
import com.example.spot.data.dtos.auth.signup.SignUpRequest
import com.example.spot.ui.presentation.auth.model.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    private val tokenManager = TokenManagerImpl()

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state = _state.asStateFlow()

    fun onSignUpClicked(username: String, email: String, password: String){
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            try {
                val request = SignUpRequest(username, email, password)
                val response = repository.signUp(request)

                tokenManager.saveTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )

                _state.update { AuthState.Success }

            } catch (e: IOException) {
                _state.update { AuthState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                _state.update { AuthState.Error("Erro no servidor") }
            } catch (e: Exception) {
                _state.update { AuthState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    fun onSignInClicked(email: String, password: String){
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            try {
                val request = SignInRequest(email, password)
                val response = repository.signIn(request)

                tokenManager.saveTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )

                _state.update { AuthState.Success }

            } catch (e: IOException) {
                _state.update { AuthState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                _state.update { AuthState.Error("Erro no servidor") }
            } catch (e: Exception) {
                _state.update { AuthState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }
}