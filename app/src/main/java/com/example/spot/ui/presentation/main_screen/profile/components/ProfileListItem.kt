package com.example.spot.ui.presentation.main_screen.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.student.R

@Composable
fun ProfileListItem(
    text: String,
    iconDark: Int? = null,
    iconLight: Int? = null,
    isLogout: Boolean = false,
    modifier: Modifier = Modifier
) {
    val arrowDark = R.drawable.arrow_right_dark
    val arrowLight = R.drawable.arrow_right_light

    val textColor = if (isLogout) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    Row(
        modifier = modifier
            .fillMaxWidth(0.95f)
            .height(45.dp)
            .shadow(
                elevation = 0.5.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (!isLogout && iconDark != null && iconLight != null) {
            Image(
                painter = painterResource(
                    if (isSystemInDarkTheme()) iconDark else iconLight
                ),
                contentDescription = "$text image",
                modifier = Modifier.size(20.dp)
            )
        } else if (isLogout) {
            Image(
                painter = painterResource(R.drawable.exit),
                contentDescription = "$text image",
                modifier = Modifier.size(17.dp),
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )

        Spacer(modifier = Modifier.weight(1f))

        if (!isLogout) {
            Icon(
                painter = painterResource(
                    if (isSystemInDarkTheme()) arrowDark else arrowLight
                ),
                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                contentDescription = "Seta para direita",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}