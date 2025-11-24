package com.example.spot.data.remote.dtos.auth.usecase.validators

class PasswordValidator {
    fun isPasswordValid(password: String): Boolean {
        if (password.isEmpty()) {
            return false
        }

        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6,30}$")

        return passwordRegex.matches(password)
    }
}