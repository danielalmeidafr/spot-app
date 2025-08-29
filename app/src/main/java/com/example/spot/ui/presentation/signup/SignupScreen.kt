package com.example.spot.ui.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.components.CustomTextField
import com.example.spot.ui.presentation.components.IconCard
import com.example.spot.ui.presentation.components.PrimaryButton
import com.student.R

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logoRes = if (isDarkTheme) {
            R.drawable.logo_dark
        } else {
            R.drawable.logo_light
        }

        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "Spot Logo",
            modifier = Modifier
                .width(150.dp)
                .height(130.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Cadastre-se",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
        )

        Spacer(modifier = Modifier.height(42.dp))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            placeholderText = "E-mail:"
        )

        Spacer(modifier = Modifier.height(27.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            placeholderText = "Senha:",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(27.dp))

        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholderText = "Confirme sua senha:",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(21.dp))

        var agreed by remember { mutableStateOf(false) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { agreed = !agreed }
        ) {
            Icon(
                painter = painterResource(
                    id = if (agreed) R.drawable.check_filled else R.drawable.check_outlined
                ),
                contentDescription = "Check",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(14.dp),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                buildAnnotatedString {
                    append("Eu concordo com os ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append("termos de privacidade")
                    }
                },
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Cadastrar",
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            "ou",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
        )

        Spacer(modifier = Modifier.height(20.dp))

        val phoneRes = if (isDarkTheme) {
            R.drawable.phone_dark
        } else {
            R.drawable.phone_light
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(43.dp, Alignment.CenterHorizontally)
        ) {
            IconCard(
                icon = painterResource(id = R.drawable.google_logo),
                contentDescription = "Login com Google",
                onClick = { }
            )

            IconCard(
                icon = painterResource(id = phoneRes),
                contentDescription = "Login com celular",
                onClick = { },
            )
        }

        Spacer(modifier = Modifier.height(15.dp))


        TextButton(
            onClick = {

            },
        ) {
            Text(
                buildAnnotatedString {
                    append("Já tem uma conta? ")
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colorScheme.primary)
                    ) {
                        append("Entre")
                    }
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}