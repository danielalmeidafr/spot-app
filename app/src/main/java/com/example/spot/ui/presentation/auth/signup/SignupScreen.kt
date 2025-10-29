package com.example.spot.ui.presentation.auth.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.ui.presentation.auth.components.CustomButton
import com.example.spot.ui.presentation.auth.components.CustomTextField
import com.example.spot.ui.presentation.auth.components.PrimaryButton
import com.example.spot.ui.presentation.auth.signup.model.SignupState
import com.example.spot.ui.presentation.auth.signup.viewmodel.SignupViewModel
import com.student.R

@Composable
fun SignupScreen(
    onBack: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val viewModel = viewModel<SignupViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    when (state) {
        SignupState.Idle -> {

        }

        SignupState.Loading -> {

        }

        is SignupState.Error -> {
            Toast.makeText(context, (state as SignupState.Error).message, Toast.LENGTH_LONG).show()
        }

        is SignupState.Success -> {
            onNavigateToMain()
        }
    }


    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
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
                Spacer(modifier = Modifier.height(50.dp))

                Surface(
                    onClick = onBack,
                    modifier = Modifier.size(55.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.background
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Crie sua conta",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Digite seu e-mail e senha para cadastrar-se.",
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
                    value = username,
                    onValueChange = { username = it },
                    placeholderText = "Nome de usuário:"
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholderText = "E-mail:"
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholderText = "Senha:",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(5.dp))

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
                    TextButton(
                        onClick = {

                        }
                    ) {
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
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                PrimaryButton(
                    text = "Cadastrar",
                    isLoading = state is SignupState.Loading,
                    onClick = {
                        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && agreed) {
                            viewModel.onSignupClicked(username, email, password)
                        } else {
                            Toast.makeText(
                                context,
                                "Preencha todos os campos e concorde com os termos.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(35.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                    )

                    Text(
                        "ou",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomButton(
                        onClick = {},
                        text = "Continuar com Google",
                        imagePainter = R.drawable.google_logo
                    )
                }
            }
        }
    }
}