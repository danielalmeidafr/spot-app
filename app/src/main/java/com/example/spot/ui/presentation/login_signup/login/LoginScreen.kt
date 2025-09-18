package com.example.spot.ui.presentation.login_signup.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.login_signup.components.CustomTextField
import com.example.spot.ui.presentation.login_signup.components.IconCard
import com.example.spot.ui.presentation.login_signup.components.PrimaryButton
import com.example.spot.ui.util.clearFocusOnTap
import com.student.R

@Composable
fun LoginScreen(
    onNavigateToSignup: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clearFocusOnTap(),
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

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "Entrar",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
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


        TextButton(
            onClick = {

            }
        ) {
            Text(
                buildAnnotatedString {
                    append("Esqueceu sua ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append("senha")
                    }
                    append("?")
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(0.95f),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        PrimaryButton(
            text = "Entrar",
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            "ou",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
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
            onClick = onNavigateToSignup
        ) {
            Text(
                buildAnnotatedString {
                    append("Não tem uma conta? ")
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colorScheme.primary)
                    ) {
                        append("Cadastre-se")
                    }
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}