package com.example.spot.ui.presentation.auth.screens.signin

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.ui.components.CustomButton
import com.example.spot.ui.components.CustomTextField
import com.example.spot.ui.components.PrimaryButton
import com.example.spot.ui.presentation.auth.model.AuthState
import com.example.spot.ui.presentation.auth.viewmodel.AuthViewModel
import com.student.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    onBack: () -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToSignup: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    val viewModel = koinViewModel<AuthViewModel>()
    val state by viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var errorOnEmailField by remember { mutableStateOf(false) }
    var errorOnPasswordField by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val shakeOffset = remember { Animatable(0f) }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val coroutineScope = rememberCoroutineScope()

    fun shake(anim: Animatable<Float, *>, intensity: Float = 10f) {
        coroutineScope.launch {
            repeat(3) {
                anim.animateTo(intensity, tween(50))
                anim.animateTo(-intensity, tween(50))
            }
            anim.animateTo(0f, tween(50))
        }
    }

    LaunchedEffect(state) {
        when (val a = state) {
            is AuthState.Error -> {
                showError = true
                errorOnEmailField = false
                errorOnPasswordField = false

                if (a.message.contains("e-mail")) {
                    errorOnEmailField = true
                    emailFocusRequester.requestFocus()
                } else if (a.message.contains("senha")) {
                    errorOnPasswordField = true
                    passwordFocusRequester.requestFocus()
                }

                shake(shakeOffset, 8f)
            }

            is AuthState.Success -> onNavigateToMain()
            else -> Unit
        }
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
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                        modifier = Modifier.padding(15.dp)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Entre na sua conta",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Digite seu e-mail e senha para entrar.",
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
                    value = email,
                    onValueChange = {
                        email = it
                        errorOnEmailField = false
                        showError = false
                    },
                    placeholderText = "E-mail:",
                    isError = errorOnEmailField,
                    modifier = Modifier.focusRequester(emailFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorOnPasswordField = false
                        showError = false
                    },
                    placeholderText = "Senha:",
                    isPassword = true,
                    isError = errorOnPasswordField,
                    modifier = Modifier.focusRequester(passwordFocusRequester)
                )

                if (showError && state is AuthState.Error) {
                    Text(
                        text = (state as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(start = 8.dp, top = 10.dp)
                            .graphicsLayer {
                                translationX = shakeOffset.value
                            }
                    )
                }

                TextButton(
                    onClick = onNavigateToForgotPassword,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .wrapContentWidth(Alignment.End),
                    colors = ButtonColors(
                        disabledContainerColor = Color.Transparent,
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    )
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
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                PrimaryButton(
                    text = "Entrar",
                    isLoading = state is AuthState.Loading,
                    onClick = {
                        val emailBlank = email.isBlank()
                        val passwordBlank = password.isBlank()

                        email = email.trim()
                        password = password.trim()

                        when {
                            emailBlank -> emailFocusRequester.requestFocus()

                            passwordBlank -> passwordFocusRequester.requestFocus()

                            else -> viewModel.onSignInClicked(email, password)
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
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        ),
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
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}