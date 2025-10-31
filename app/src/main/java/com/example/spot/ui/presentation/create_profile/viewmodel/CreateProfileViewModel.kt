package com.example.spot.ui.presentation.create_profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.dtos.create_profile.CreateProfileRepository
import com.example.spot.data.dtos.create_profile.CreateProfileRequest
import com.example.spot.ui.presentation.create_profile.model.CreateProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class CreateProfileViewModel(
    private val repository: CreateProfileRepository
) : ViewModel() {
    private val _state = MutableStateFlow<CreateProfileState>(CreateProfileState.Idle)
    val state = _state.asStateFlow()

    fun onCreateProfileClicked(fullName: String, nickname: String, birthDate: String) {
        _state.update { CreateProfileState.Loading }

        viewModelScope.launch {
            try {
                val request = CreateProfileRequest(fullName, nickname, birthDate)
                repository.createProfile(request)

                _state.update { CreateProfileState.Success }

            } catch (e: IOException) {
                _state.update { CreateProfileState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                _state.update { CreateProfileState.Error("Erro no servidor") }
            } catch (e: Exception) {
                _state.update { CreateProfileState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }
}