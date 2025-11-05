package com.example.spot.ui.presentation.auth.components

fun emailError(email: String): Boolean {
    if (email.isEmpty()) {
        return false
    }

    val emailRegex = Regex("^[A-Za-z0-9](?:[A-Za-z0-9._%+-]*[A-Za-z0-9])?@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    return !emailRegex.matches(email)
}