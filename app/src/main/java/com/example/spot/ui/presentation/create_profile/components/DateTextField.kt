package com.example.spot.ui.presentation.create_profile.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.spot.ui.presentation.components.CustomTextField

@Composable
fun DateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Data de nascimento:"
) {
    CustomTextField(
        value = value,
        onValueChange = { newValue ->
            val digits = newValue.filter { it.isDigit() }
            if (digits.length <= 8) {
                onValueChange(digits)
            }
        },
        placeholderText = placeholderText,
        modifier = modifier,

        visualTransformation = DateVisualTransformation(),
        keyboardType = KeyboardType.Number
    )
}