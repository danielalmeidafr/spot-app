package com.example.spot.ui.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.login.components.CustomTextField
import com.example.spot.ui.presentation.login.components.IconCard
import com.student.R

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
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
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        val logoRes = if (isDarkTheme) {
            R.drawable.logo_dark
        } else {
            R.drawable.logo_light
        }

        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "Spot Logo",
            modifier = Modifier
                .width(170.dp)
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Entrar",
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

        Spacer(modifier = Modifier.height(12.dp))

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
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
            modifier = Modifier.fillMaxWidth(0.9f),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

            },
            modifier = Modifier
                .width(260.dp)
                .height(39.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                "Entrar",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        /*Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            )*/
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