package com.example.spot.ui.presentation.create_profile

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.ui.components.CustomTextField
import com.example.spot.ui.components.PrimaryButton
import com.example.spot.ui.presentation.create_profile.components.DateTextField
import com.example.spot.ui.presentation.create_profile.components.GenderDropdown
import com.example.spot.ui.presentation.create_profile.model.CreateProfileState
import com.example.spot.ui.presentation.create_profile.viewmodel.CreateProfileViewModel
import com.student.R
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
    var gender by remember { mutableStateOf("") }

    val fullNameFocusRequester = remember { FocusRequester() }
    val nicknameFocusRequester = remember { FocusRequester() }
    val birthDateFocusRequester = remember { FocusRequester() }
    val genderFocusRequester = remember { FocusRequester() }

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
                    "Digite suas informações pessoais para criação da conta.",
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

                Image(
                    painter = painterResource(R.drawable.user_image),
                    contentDescription = "Profile image",
                    modifier = Modifier.size(90.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholderText = "Nome completo:",
                    modifier = Modifier.focusRequester(fullNameFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = nickname,
                    onValueChange = { nickname = it },
                    placeholderText = "Nome de usuário:",
                    modifier = Modifier.focusRequester(nicknameFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                DateTextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    modifier = Modifier.focusRequester(birthDateFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                GenderDropdown(
                    selectedGender = gender,
                    onGenderSelected = { gender = it },
                    modifier = Modifier.focusRequester(genderFocusRequester)
                )

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = "Finalizar",
                    isLoading = state is CreateProfileState.Loading,
                    onClick = {
                        var genderSelected: String = ""
                        genderSelected = when (gender) {
                            "Masculino" -> "MALE"

                            "Feminino" -> "FEMALE"

                            else -> "OTHER"
                        }

                        when {
                            fullName.isBlank() -> fullNameFocusRequester.requestFocus()
                            nickname.isBlank() -> nicknameFocusRequester.requestFocus()
                            birthDate.isBlank() -> birthDateFocusRequester.requestFocus()
                            gender.isEmpty() -> genderFocusRequester.requestFocus()
                            else -> viewModel.onCreateProfileClicked(fullName, nickname, birthDate, genderSelected)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}