package com.example.spot.ui.presentation.create_profile

import DateTextField
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.ui.presentation.components.CustomTextField
import com.example.spot.ui.presentation.components.PrimaryButton
import com.student.R

@Composable
fun CreateProfileContent(
    fullName: String,
    nickname: String,
    birthDate: String,
    onFullNameChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,
    onFinishClick: () -> Unit,
    isLoading: Boolean,
    fullNameFocusRequester: FocusRequester = remember { FocusRequester() },
    nicknameFocusRequester: FocusRequester = remember { FocusRequester() },
    birthDateFocusRequester: FocusRequester = remember { FocusRequester() }
) {
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
                    onValueChange = onFullNameChange,
                    placeholderText = "Nome completo:",
                    modifier = Modifier.focusRequester(fullNameFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                CustomTextField(
                    value = nickname,
                    onValueChange = onNicknameChange,
                    placeholderText = "Nome de usuário:",
                    modifier = Modifier.focusRequester(nicknameFocusRequester)
                )

                Spacer(modifier = Modifier.height(30.dp))

                DateTextField(
                    value = birthDate,
                    onValueChange = onBirthDateChange,
                    modifier = Modifier.focusRequester(birthDateFocusRequester)
                )

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = "Finalizar",
                    isLoading = isLoading,
                    onClick = onFinishClick
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}