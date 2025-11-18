package com.example.spot.data.remote.dtos.auth.usecase.validators

class EmailValidator {
    fun isEmailValid(email: String): Boolean {
        if (email.isBlank()) {
            return false
        }

        val emailRegex = Regex("^[A-Za-z0-9](?:[A-Za-z0-9._%+-]*[A-Za-z0-9])?@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        return emailRegex.matches(email)
    }
}