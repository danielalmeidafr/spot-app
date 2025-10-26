package com.example.spot.ui.presentation.main_screen.profile.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.student.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    isLogout: Boolean = false
) {
    val textColor = if (isLogout) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground
    val iconTint = if (isLogout) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onBackground
    val shape = RoundedCornerShape(5.dp)

    var isPressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = tween(durationMillis = 600),
        label = "scaleAnim"
    )

    Row(
        modifier = modifier
            .fillMaxWidth(0.95f)
            .height(45.dp)
            .scale(scale)
            .shadow(0.5.dp, shape)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true)
            ) {
                isPressed = true
                scope.launch {
                    delay(100)
                    isPressed = false
                }
            }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = iconTint,
            contentDescription = "$text icon",
            modifier = Modifier.size(15.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )

        Spacer(modifier = Modifier.weight(1f))

        if (!isLogout) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                contentDescription = "Seta para direita",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}