package com.example.spot.ui.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.dtos.auth.AuthRepository
import com.example.spot.data.dtos.auth.ConfirmCodeRequest
import com.example.spot.data.dtos.auth.ForgotPasswordRequest
import com.example.spot.data.dtos.auth.SignInRequest
import com.example.spot.data.dtos.auth.SignUpRequest
import com.example.spot.ui.presentation.auth.model.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state = _state.asStateFlow()

    fun onSignUpClicked(email: String, password: String) {
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            try {
                val request = SignUpRequest(email, password)
                val response = repository.signUp(request)

                repository.saveTokens(response.accessToken, response.refreshToken)

                _state.update { AuthState.Success }

            } catch (e: IOException) {
                _state.update { AuthState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                val message = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    val json = JSONObject(errorBody ?: "")

                    json.optString("message", "Erro no servidor")
                } catch (_: Exception) {
                    "Erro no servidor"
                }

                _state.update { AuthState.Error(message) }
            } catch (e: Exception) {
                _state.update { AuthState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    fun onSignInClicked(email: String, password: String) {
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            try {
                val request = SignInRequest(email, password)
                val response = repository.signIn(request)

                repository.saveTokens(response.accessToken, response.refreshToken)

                _state.update { AuthState.Success }

            } catch (e: IOException) {
                _state.update { AuthState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                val message = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    val json = JSONObject(errorBody ?: "")

                    json.optString("message", "Erro no servidor")
                } catch (_: Exception) {
                    "Erro no servidor"
                }

                _state.update { AuthState.Error(message) }
            } catch (e: Exception) {
                _state.update { AuthState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    fun onForgotPasswordClicked(emailOrNickname: String){
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            try {
                val request = ForgotPasswordRequest(emailOrNickname)
                repository.forgotPassword(request)

                _state.update { AuthState.Success }
            } catch (e: IOException) {
                _state.update { AuthState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                val message = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    val json = JSONObject(errorBody ?: "")

                    json.optString("message", "Erro no servidor")
                } catch (_: Exception) {
                    "Erro no servidor"
                }

                _state.update { AuthState.Error(message) }
            } catch (e: Exception) {
                _state.update { AuthState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    fun onConfirmCodeClicked(code: String){
        _state.update { AuthState.Loading }

        viewModelScope.launch {
            try {
                val request = ConfirmCodeRequest(code)
                repository.confirmCode(request)

                _state.update { AuthState.Success }
            } catch (e: IOException) {
                _state.update { AuthState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                val message = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    val json = JSONObject(errorBody ?: "")

                    json.optString("message", "Erro no servidor")
                } catch (_: Exception) {
                    "Erro no servidor"
                }

                _state.update { AuthState.Error(message) }
            } catch (e: Exception) {
                _state.update { AuthState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }
}