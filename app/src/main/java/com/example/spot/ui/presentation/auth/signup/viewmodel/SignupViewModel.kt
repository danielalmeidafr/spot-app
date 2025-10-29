package com.example.spot.ui.presentation.auth.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.dtos.auth.signup.SignupRepository
import com.example.spot.data.dtos.auth.signup.SignupRequest
import com.example.spot.data.dtos.auth.signup.TokenManager
import com.example.spot.data.dtos.auth.signup.TokenManagerImpl
import com.example.spot.ui.presentation.auth.signup.model.SignupState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class SignupViewModel : ViewModel() {
    private val repository = SignupRepository()
    private val tokenManager = TokenManagerImpl()

    private val _state = MutableStateFlow<SignupState>(SignupState.Idle)
    val state = _state.asStateFlow()

    fun onSignupClicked(username: String, email: String, password: String){
        _state.update { SignupState.Loading }

        viewModelScope.launch {
            try {
                val request = SignupRequest(username, email, password)
                val response = repository.signup(request)

                tokenManager.saveTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )

                _state.update { SignupState.Success }

            } catch (e: IOException) {
                _state.update { SignupState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                _state.update { SignupState.Error("Erro no servidor") }
            } catch (e: Exception) {
                _state.update { SignupState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }
}