package com.example.spot.ui.presentation.auth.screens.confirm_code

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfirmCodeScreen(
    onBack: () -> Unit,
    onNavigateToCreateProfile: () -> Unit
) {
    val viewModel = koinViewModel<AuthViewModel>()
    val state by viewModel.state.collectAsState()
    val isKeyboardVisible = rememberKeyboardVisibility()
    val snackbarHostState = remember { SnackbarHostState() }

    var code by remember { mutableStateOf("") }

    val codeFocusRequester = remember { FocusRequester() }

    LaunchedEffect(state) {
        when (val a = state) {
            is AuthState.Error -> {
                snackbarHostState.showSnackbar(
                    message = a.message,
                    duration = SnackbarDuration.Short
                )
            }
            is AuthState.Success -> onNavigateToCreateProfile()
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
                .padding(bottom = if (isKeyboardVisible) 320.dp else 0.dp)
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
                    "Confirme sua Identidade",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Insira o código enviado para o seu email.",
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
                    value = code,
                    onValueChange = { code = it },
                    placeholderText = "Código:",
                    modifier = Modifier.focusRequester(codeFocusRequester)
                )

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = "Verificar",
                    isLoading = state is AuthState.Loading,
                    onClick = {
                        when {
                            code.isBlank() -> codeFocusRequester.requestFocus()
                            else -> viewModel.onConfirmCodeClicked(code)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}