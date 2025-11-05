package com.example.spot.ui.presentation.main_screen.profile.components.sections

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.main_screen.profile.components.ListItem
import com.student.R

@Composable
fun OptionsSection(
    onLogout: () -> Unit,
    isLoading: Boolean
) {
    Spacer(modifier = Modifier.height(20.dp))

    ListItem(text = "Segurança e senha", icon = R.drawable.security)
    Spacer(modifier = Modifier.height(15.dp))

    ListItem(text = "Editar perfil", icon = R.drawable.profile_edit)
    Spacer(modifier = Modifier.height(15.dp))

    ListItem(text = "Métodos de pagamentos", icon = R.drawable.payments)
    Spacer(modifier = Modifier.height(30.dp))

    ListItem(text = "Notificações", icon = R.drawable.notifications)
    Spacer(modifier = Modifier.height(15.dp))

    ListItem(text = "Ajuda e suporte", icon = R.drawable.help)
    Spacer(modifier = Modifier.height(30.dp))

    ListItem(text = "Sair", icon = R.drawable.exit, isLogout = true, onClick = onLogout, isLoading = isLoading)
    Spacer(modifier = Modifier.height(20.dp))
}