package com.example.spot.data.remote.dtos.auth.usecase.password

import com.example.spot.data.remote.dtos.auth.AuthRepository
import com.example.spot.data.remote.dtos.auth.password.NewPasswordRequest
import com.example.spot.data.remote.dtos.auth.usecase.ErrorCause
import com.example.spot.data.remote.dtos.auth.usecase.Result
import com.example.spot.data.remote.dtos.auth.usecase.validators.PasswordValidator
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class NewPasswordUseCase(
    private val repository: AuthRepository,
    private val passwordValidator: PasswordValidator
) {

    suspend operator fun invoke(email: String, code: String, newPassword: String): Result {
        val trimmedPassword = newPassword.trim()

        if (!passwordValidator.isPasswordValid(trimmedPassword)) {
            return Result.Error(ErrorCause.ValidationError("A senha informada não é valida."))
        }

        try {
            val request = NewPasswordRequest(email, code, newPassword)
            repository.newPassword(request)

            return Result.Success

        } catch (_: IOException) {
            return Result.Error(ErrorCause.ApiError("Falha na conexão"))
        } catch (e: HttpException) {
            val message = try {
                val errorBody = e.response()?.errorBody()?.string()
                val json = JSONObject(errorBody ?: "")

                json.optString("message", "Erro no servidor")
            } catch (_: Exception) {
                "Erro no servidor"
            }
            return Result.Error(ErrorCause.ApiError(message))
        } catch (e: Exception) {
            return Result.Error(ErrorCause.ApiError("Ocorreu um erro inesperado: ${e.message}"))
        }
    }
}