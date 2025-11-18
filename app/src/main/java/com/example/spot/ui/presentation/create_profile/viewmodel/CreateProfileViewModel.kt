package com.example.spot.ui.presentation.create_profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.create_profile.CreateProfileDto
import com.example.spot.data.remote.dtos.create_profile.CreateProfileRepository
import com.example.spot.data.remote.dtos.create_profile.CreateProfileRequest
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

    fun onCreateProfileClicked(fullName: String, nickname: String, birthDate: String, gender: String) {
        _state.update { CreateProfileState.Loading }

        viewModelScope.launch {
            try {
                val newDate = convertDateForApi(birthDate)

                val request = CreateProfileRequest(CreateProfileDto(nickname, fullName, newDate, gender))
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

fun convertDateForApi(dateString: String): String {
    val day = dateString.take(2)
    val month = dateString.substring(2, 4)
    val year = dateString.substring(4, 8)

    return "$year-$month-$day"
}