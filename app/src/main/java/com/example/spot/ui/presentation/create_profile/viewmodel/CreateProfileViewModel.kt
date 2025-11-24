package com.example.spot.ui.presentation.create_profile.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.create_profile.CreateProfileDto
import com.example.spot.data.remote.dtos.create_profile.CreateProfileRepository
import com.example.spot.data.remote.dtos.create_profile.CreateProfileRequest
import com.example.spot.ui.presentation.create_profile.model.CreateProfileState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream

class CreateProfileViewModel(
    private val repository: CreateProfileRepository
) : ViewModel() {
    private val _state = MutableStateFlow<CreateProfileState>(CreateProfileState.Idle)
    val state = _state.asStateFlow()

    fun onCreateProfileClicked(context: Context, fullName: String, nickName: String, birthDate: String, gender: String, photoUri: Uri?) {
        _state.update { CreateProfileState.Loading }

        viewModelScope.launch {
            try {
                val newDate = convertDateForApi(birthDate)

                val profileDto = CreateProfileDto(nickName, fullName, newDate, gender)
                val requestObj = CreateProfileRequest(profileDto)

                val jsonString = Gson().toJson(requestObj)

                val dataPart = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

                val imagePart = prepareImagePart(context, photoUri)

                repository.createProfile(dataPart, imagePart)

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

private fun prepareImagePart(context: Context, uri: Uri?): MultipartBody.Part? {
    if (uri == null) return null

    val contentResolver = context.contentResolver
    val type = contentResolver.getType(uri) ?: "image/jpeg"

    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)

    contentResolver.openInputStream(uri)?.use { inputStream ->
        FileOutputStream(tempFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    val requestFile = tempFile.asRequestBody(type.toMediaTypeOrNull())

    return MultipartBody.Part.createFormData("image", tempFile.name, requestFile)
}

fun convertDateForApi(dateString: String): String {
    val day = dateString.take(2)
    val month = dateString.substring(2, 4)
    val year = dateString.substring(4, 8)

    return "$year-$month-$day"
}