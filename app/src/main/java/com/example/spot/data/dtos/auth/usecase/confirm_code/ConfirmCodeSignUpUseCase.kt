package com.example.spot.data.dtos.auth.usecase.confirm_code

import com.example.spot.data.dtos.auth.AuthRepository
import com.example.spot.data.dtos.auth.code.ConfirmCodeSignUpRequest
import com.example.spot.data.dtos.auth.usecase.ErrorCause
import com.example.spot.data.dtos.auth.usecase.Result
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class ConfirmCodeSignUpUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(email: String, code: String, password: String): Result {

        try {
            val request = ConfirmCodeSignUpRequest(email, code, password)
            val response = repository.confirmCodeSignUp(request)

            repository.saveTokens(response.accessToken, response.refreshToken)

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