package com.example.spot.ui.presentation.main_screen.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.auth.AuthRepository
import com.example.spot.data.remote.dtos.auth.sign.SignOutRequest
// 1. Importe o novo repositório
import com.example.spot.data.remote.dtos.UserPreferencesRepository
import com.example.spot.data.remote.dtos.profile.ProfileRepository
import com.example.spot.data.remote.dtos.profile.toInfoData
import com.example.spot.data.remote.dtos.profile.toProgressData
import com.example.spot.data.remote.dtos.profile.toStatsData
import com.example.spot.ui.presentation.main_screen.profile.model.InfoData
import com.example.spot.ui.presentation.main_screen.profile.model.ProfileState
import com.example.spot.ui.presentation.main_screen.profile.model.ProgressData
import com.example.spot.ui.presentation.main_screen.profile.model.StatsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = _state.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _isLoggingOut = MutableStateFlow(false)
    val isLoggingOut = _isLoggingOut.asStateFlow()

    private val _isCheckingLogin = MutableStateFlow(true)
    val isCheckingLogin = _isCheckingLogin.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.accessToken.collect { token ->
                val logged = token.isNotEmpty()
                _isLoggedIn.value = logged
                _isCheckingLogin.value = false

                if (logged && _state.value is ProfileState.Loading) {
                    try {
                        val response = profileRepository.getInfoProfile()

                        val profileInfo = response.profile.toInfoData()
                        val profileProgress = response.toProgressData()
                        val profileStats = response.toStatsData()

                        _state.update {
                            ProfileState.Success(
                                infoData = profileInfo,
                                progressData = profileProgress,
                                statsData = profileStats
                            )
                        }
                    } catch (e: HttpException) {
                        if (e.code() == 404) {
                            println("Perfil não encontrado (404). O usuário deve estar criando o perfil.")
                        } else {
                            _state.update { ProfileState.Error("Erro no servidor: ${e.message}") }
                        }
                    } catch (e: IOException) {
                        _state.update { ProfileState.Error("Falha na conexão") }
                    } catch (e: Exception) {
                        _state.update { ProfileState.Error("Erro desconhecido") }
                    }
                } else if (!logged) {
                    _state.value = ProfileState.Loading
                }
            }
        }
    }
    fun signOut() {
        _isLoggingOut.value = true

        viewModelScope.launch {
            try {
                val refreshToken = userPreferencesRepository.refreshToken.first()

                val request = SignOutRequest(refreshToken)
                authRepository.signOut(request)

            } catch (e: IOException) {
                _state.update { ProfileState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                val message = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    val json = JSONObject(errorBody ?: "")

                    json.optString("message", "Erro no servidor")
                } catch (_: Exception) {
                    "Erro no servidor"
                }

                _state.update { ProfileState.Error(message) }
            } catch (e: Exception) {
                _state.update { ProfileState.Error("Ocorreu um erro: ${e.message}") }
            } finally {
                userPreferencesRepository.clearTokens()
                _isLoggedIn.value = false
                _isLoggingOut.value = false
            }
        }
    }
}