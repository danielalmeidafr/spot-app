package com.example.spot.ui.presentation.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.student.R

@Composable
fun PasswordRequirementItem(
    text: String,
    isValid: Boolean
) {
    val colorId = if (isValid) Color(0xFF4CAF50) else Color.Gray.copy(0.5f)

    Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(R.drawable.check),
            contentDescription = null,
            modifier = Modifier.size(13.dp),
            colorFilter = ColorFilter.tint(colorId)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}