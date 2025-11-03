package com.example.spot.ui.presentation.auth.screens.signup

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.SnackbarDuration
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
fun SignUpScreen(
    onBack: () -> Unit,
    onNavigateToConfirmCode: () -> Unit
) {
    val viewModel = koinViewModel<AuthViewModel>()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordMismatch by remember { mutableStateOf(false) }
    var agreed by remember { mutableStateOf(false) }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmFocusRequester = remember { FocusRequester() }

    val passwordShakeOffset = remember { Animatable(0f) }
    val termsShakeOffset = remember { Animatable(0f) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(password, confirmPassword) {
        passwordMismatch = confirmPassword.isNotEmpty() && (password != confirmPassword)
    }

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
                snackbarHostState.showSnackbar(
                    message = a.message,
                    duration = SnackbarDuration.Short
                )
            }
            is AuthState.Success -> {
                onNavigateToConfirmCode()
            }
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholderText = "E-mail:",
                    modifier = Modifier.focusRequester(emailFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholderText = "Senha:",
                    isPassword = true,
                    modifier = Modifier.focusRequester(passwordFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholderText = "Confirmar a senha:",
                    isPassword = true,
                    modifier = Modifier.focusRequester(confirmFocusRequester)
                )

                Spacer(modifier = Modifier.height(5.dp))

                if (passwordMismatch) {
                    Text(
                        text = "As senhas não coincidem",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(start = 8.dp)
                            .graphicsLayer {
                                translationX = passwordShakeOffset.value
                            }
                    )
                } else {
                    Spacer(modifier = Modifier.height(5.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { agreed = !agreed }
                        .graphicsLayer { translationX = termsShakeOffset.value }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (agreed) R.drawable.check_filled else R.drawable.check_outlined
                        ),
                        contentDescription = "Check",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                    TextButton(onClick = {}) {
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
                    isLoading = state is AuthState.Loading,
                    onClick = {
                        when {
                            email.isBlank() -> emailFocusRequester.requestFocus()
                            password.isBlank() -> passwordFocusRequester.requestFocus()
                            confirmPassword.isBlank() -> confirmFocusRequester.requestFocus()
                            passwordMismatch -> shake(passwordShakeOffset, 8f)
                            !agreed -> shake(termsShakeOffset, 8f)
                            else -> viewModel.onSignUpClicked(email, password)
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
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
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