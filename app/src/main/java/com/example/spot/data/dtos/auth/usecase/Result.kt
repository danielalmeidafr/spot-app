package com.example.spot.data.dtos.auth.usecase

sealed class Result {
    data object Success: Result()
    data class Error(val cause: ErrorCause): Result()
}

sealed class ErrorCause {
    data class ValidationError(val message: String) : ErrorCause()
    data class ApiError(val message: String) : ErrorCause()
}