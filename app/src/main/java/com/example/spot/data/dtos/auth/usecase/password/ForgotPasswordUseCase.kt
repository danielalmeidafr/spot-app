package com.example.spot.data.dtos.auth.usecase.password

import com.example.spot.data.dtos.auth.AuthRepository
import com.example.spot.data.dtos.auth.password.ForgotPasswordRequest
import com.example.spot.data.dtos.auth.usecase.ErrorCause
import com.example.spot.data.dtos.auth.usecase.Result
import com.example.spot.data.dtos.auth.usecase.validators.EmailValidator
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class ForgotPasswordUseCase(
    private val repository: AuthRepository,
    private val emailValidator: EmailValidator
) {

    suspend operator fun invoke(email: String): Result {
        val trimmedEmail = email.trim()

        if (!emailValidator.isEmailValid(trimmedEmail)) {
            return Result.Error(ErrorCause.ValidationError("O e-mail informado não é valido."))
        }

        try {
            val request = ForgotPasswordRequest(trimmedEmail)
            repository.forgotPassword(request)

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