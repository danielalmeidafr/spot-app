package com.example.spot.ui.presentation.main_screen.profile.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.student.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileListItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    isLogout: Boolean = false
) {
    val textColor = if (isLogout) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground
    var isPressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val arrowOffset by animateDpAsState(
        targetValue = if (isPressed) 5.dp else 0.dp,
        animationSpec = tween(durationMillis = 600),
        label = "arrowOffset"
    )

    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true)
            ) {
                isPressed = true
                scope.launch {
                    delay(150)
                    isPressed = false
                }
            }
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
        val iconTint = if (isLogout) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground

        Icon(
            painter = painterResource(id = icon),
            tint = iconTint,
            contentDescription = "$text image",
            modifier = Modifier.size(15.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )

        Spacer(modifier = Modifier.weight(1f))

        if (!isLogout) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                contentDescription = "Seta para direita",
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = arrowOffset)
            )
        }
    }
}