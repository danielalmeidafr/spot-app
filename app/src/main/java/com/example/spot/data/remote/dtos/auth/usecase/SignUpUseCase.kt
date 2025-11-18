package com.example.spot.data.remote.dtos.auth.usecase

import com.example.spot.data.remote.dtos.auth.AuthRepository
import com.example.spot.data.remote.dtos.auth.sign.SignUpRequest
import com.example.spot.data.remote.dtos.auth.usecase.validators.EmailValidator
import com.example.spot.data.remote.dtos.auth.usecase.validators.PasswordValidator
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class SignUpUseCase(
    private val repository: AuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) {

    suspend operator fun invoke(email: String, password: String) : Result {
        val (trimmedEmail, trimmedPassword) = email.trim() to password.trim()

        if (!emailValidator.isEmailValid(trimmedEmail)) {
            return Result.Error(ErrorCause.ValidationError("O e-mail informado não é válido."))
        }
        if (!passwordValidator.isPasswordValid(trimmedPassword)) {
            return Result.Error(ErrorCause.ValidationError("A senha informada não é valida."))
        }

        try {
            val request = SignUpRequest(trimmedEmail)
            repository.signUp(request)

            return Result.Success
        } catch (e: IOException) {
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