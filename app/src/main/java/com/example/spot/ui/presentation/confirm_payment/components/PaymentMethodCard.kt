package com.example.spot.ui.presentation.confirm_payment.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.confirm_payment.PaymentIcon
import com.student.R

@Composable
fun PaymentMethodCard(
    modifier: Modifier = Modifier,
    text: String,
    agreed: Boolean,
    icon: PaymentIcon,
    onToggle: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(60.dp)
            .shadow(0.5.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
                onClick = onToggle
            ),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (icon) {
                    is PaymentIcon.ImageRes -> {
                        Image(
                            painter = painterResource(icon.res),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    is PaymentIcon.VectorRes -> {
                        Icon(
                            painter = painterResource(icon.res),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Icon(
                painter = painterResource(
                    id = if (agreed) R.drawable.check_filled else R.drawable.check_outlined
                ),
                contentDescription = "Check",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(14.dp)
                    .clickable { onToggle() }
            )
        }
    }
}