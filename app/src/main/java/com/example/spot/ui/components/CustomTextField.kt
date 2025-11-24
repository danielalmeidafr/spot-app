package com.example.spot.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.core.theme.Montserrat
import com.student.R

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholderText,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        textStyle = TextStyle(
            fontSize = 12.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(50.dp)
            .border(
                width = 0.2.dp,
                color = if (isError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .shadow(
                elevation = 0.5.dp,
                shape = RoundedCornerShape(15),
                clip = false
            ),
        shape = RoundedCornerShape(15),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            cursorColor = if (isError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
        ),
        maxLines = 1,

        visualTransformation = if (isPassword) {
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            visualTransformation
        },

        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

        trailingIcon = {
            if (isPassword) {
                val imageRes = if (passwordVisible) R.drawable.eye_open else R.drawable.eye_closed

                Icon(
                    painter = painterResource(id = imageRes),
                    tint = if (isError) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground,
                    contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 15.dp)
                        .alpha(0.5f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            passwordVisible = !passwordVisible
                        }
                )
            }
        }
    )
}