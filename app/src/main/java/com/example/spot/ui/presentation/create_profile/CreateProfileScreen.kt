package com.example.spot.ui.presentation.create_profile

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.spot.core.theme.SpotTheme
import com.example.spot.ui.presentation.create_profile.model.CreateProfileState
import com.example.spot.ui.presentation.create_profile.viewmodel.CreateProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateProfileScreen(
    onNavigateToMain: () -> Unit,
    viewModel: CreateProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    val fullNameFocusRequester = remember { FocusRequester() }
    val nicknameFocusRequester = remember { FocusRequester() }
    val birthDateFocusRequester = remember { FocusRequester() }

    LaunchedEffect(state) {
        when (val a = state) {
            is CreateProfileState.Error -> {
                Toast.makeText(context, a.message, Toast.LENGTH_LONG).show()
            }
            is CreateProfileState.Success -> {
                onNavigateToMain()
            }
            else -> Unit
        }
    }

    CreateProfileContent(
        fullName = fullName,
        nickname = nickname,
        birthDate = birthDate,
        onFullNameChange = { fullName = it },
        onNicknameChange = { nickname = it },
        onBirthDateChange = { birthDate = it },
        onFinishClick = {
            when {
                fullName.isBlank() -> fullNameFocusRequester.requestFocus()
                nickname.isBlank() -> nicknameFocusRequester.requestFocus()
                birthDate.isBlank() -> birthDateFocusRequester.requestFocus()
                else -> viewModel.onCreateProfileClicked(fullName, nickname, birthDate)
            }
        },
        isLoading = state is CreateProfileState.Loading,
        fullNameFocusRequester = fullNameFocusRequester,
        nicknameFocusRequester = nicknameFocusRequester,
        birthDateFocusRequester = birthDateFocusRequester
    )
}

@Preview(showBackground = true)
@Composable
fun CreateProfilePreview() {
    SpotTheme {
        CreateProfileContent(
            fullName = "",
            nickname = "",
            birthDate = "",
            onFullNameChange = {},
            onNicknameChange = {},
            onBirthDateChange = {},
            onFinishClick = {},
            isLoading = false
        )
    }
}