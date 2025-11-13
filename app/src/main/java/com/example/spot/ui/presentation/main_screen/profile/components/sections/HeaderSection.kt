package com.example.spot.ui.presentation.main_screen.profile.components.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.student.R

@Composable
fun HeaderSection(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val themeIconRes = if (isDarkTheme) R.drawable.light else R.drawable.dark

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            "PERFIL",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Center)
        )

        Icon(
            painter = painterResource(id = themeIconRes),
            contentDescription = if (isDarkTheme) "Ativar modo claro" else "Ativar modo escuro",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
                .size(18.dp)
                .clickable { onThemeToggle() }
        )
    }
}