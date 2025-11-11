package com.example.spot.ui.presentation.auth.screens.new_password

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.core.util.rememberKeyboardVisibility
import com.example.spot.ui.components.CustomTextField
import com.example.spot.ui.components.PrimaryButton
import com.example.spot.ui.presentation.auth.model.AuthState
import com.example.spot.ui.presentation.auth.viewmodel.AuthViewModel
import com.student.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewPasswordScreen(
    onBack: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    email: String,
    code: String
) {
    val viewModel = koinViewModel<AuthViewModel>()
    val state by viewModel.state.collectAsState()
    val isKeyboardVisible = rememberKeyboardVisibility()

    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    var errorOnNewPasswordField by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    var passwordMismatch by remember { mutableStateOf(false) }

    val newPasswordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    val shakeOffset = remember { Animatable(0f) }

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

    LaunchedEffect(newPassword, confirmNewPassword) {
        passwordMismatch = confirmNewPassword.isNotBlank() && (newPassword != confirmNewPassword)
    }


    LaunchedEffect(state) {
        when (val a = state) {
            is AuthState.Error -> {
                showError = true
                errorOnNewPasswordField = false

                if (a.message.contains("senha")) {
                    errorOnNewPasswordField = true
                    newPasswordFocusRequester.requestFocus()
                }

                shake(shakeOffset, 8f)
            }

            is AuthState.Success -> onNavigateToSignIn()

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
                .padding(bottom = if (isKeyboardVisible) 350.dp else 0.dp)
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
                    "Redefina sua senha",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Insira uma nova senha para sua conta.",
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
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                        errorOnNewPasswordField = false
                        showError = false
                    },
                    placeholderText = "Nova senha:",
                    isError = errorOnNewPasswordField,
                    isPassword = true,
                    modifier = Modifier.focusRequester(newPasswordFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = confirmNewPassword,
                    onValueChange = { confirmNewPassword = it },
                    placeholderText = "Confirmar nova senha:",
                    isError = passwordMismatch,
                    isPassword = true,
                    modifier = Modifier.focusRequester(confirmPasswordFocusRequester)
                )

                if (passwordMismatch) {
                    Text(
                        text = "As senhas não coincidem",
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

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = "Continuar",
                    isLoading = state is AuthState.Loading,
                    onClick = {
                        val newPasswordBlank = newPassword.isBlank()
                        val confirmNewPasswordBlank = confirmNewPassword.isBlank()

                        newPassword = newPassword.trim()

                        when {
                            newPasswordBlank -> newPasswordFocusRequester.requestFocus()

                            confirmNewPasswordBlank -> confirmPasswordFocusRequester.requestFocus()

                            passwordMismatch -> {
                                confirmPasswordFocusRequester.requestFocus()
                                shake(shakeOffset, 8f)
                            }

                            else -> viewModel.onNewPasswordClicked(email, code, newPassword)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}