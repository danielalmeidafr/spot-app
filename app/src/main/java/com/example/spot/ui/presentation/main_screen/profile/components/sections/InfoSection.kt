package com.example.spot.ui.presentation.main_screen.profile.components.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spot.ui.presentation.main_screen.profile.model.InfoData
import com.student.R

@Composable
fun InfoSection(
    infoData: InfoData
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        ) {
            AsyncImage(
                model = infoData.profileImageUrl,
                contentDescription = "Sua foto de perfil",
                error = painterResource(R.drawable.user_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = infoData.fullName,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = infoData.nickname,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}