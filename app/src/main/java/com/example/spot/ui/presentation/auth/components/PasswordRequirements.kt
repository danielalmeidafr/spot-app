package com.example.spot.ui.presentation.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun PasswordRequirements(
    password: String
) {
    val isLengthValid = password.length in 8..30
    val hasNumber = password.any { it.isDigit() }
    val hasUpperCase = password.any { it.isUpperCase() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PasswordRequirementItem(
            text = "8 a 30 caracteres",
            isValid = isLengthValid
        )
        PasswordRequirementItem(
            text = "Pelo menos 1 número",
            isValid = hasNumber
        )
        PasswordRequirementItem(
            text = "Pelo menos 1 letra maiúscula",
            isValid = hasUpperCase
        )
        PasswordRequirementItem(
            text = "Pelo menos 1 caractere especial",
            isValid = hasSpecialChar
        )
    }
}