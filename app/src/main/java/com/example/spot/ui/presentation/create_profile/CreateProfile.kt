package com.example.spot.ui.presentation.create_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.core.theme.SpotTheme
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.ui.presentation.auth.components.CustomTextField
import com.example.spot.ui.presentation.auth.components.PrimaryButton

@Composable
fun CreateProfile(
    onNavigateToMain: () -> Unit
) {
    var fullName by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var birthDate by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clearFocusOnTap()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .statusBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                Text(
                    "Crie o seu perfil",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Digite seu nome, telefone e data de nascimento para criar seu perfil.",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                )

                Spacer(modifier = Modifier.height(30.dp))
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                CustomTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholderText = "Nome completo:"
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholderText = "Nome de usuário:"
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    placeholderText = "Data de nascimento:",
                )

                Spacer(modifier = Modifier.height(40.dp))

                PrimaryButton(
                    text = "Finalizar",
                    onClick = {
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateProfilePreview() {
    SpotTheme {
        CreateProfile(
            onNavigateToMain = {}
        )
    }
}