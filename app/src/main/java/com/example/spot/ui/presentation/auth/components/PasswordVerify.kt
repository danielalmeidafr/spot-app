package com.example.spot.ui.presentation.auth.components

fun passwordError(password: String): Boolean {
    if (password.isEmpty()) {
        return false
    }

    val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{6,30}$")

    return !passwordRegex.matches(password)
}