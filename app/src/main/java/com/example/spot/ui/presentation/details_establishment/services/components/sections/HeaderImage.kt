package com.example.spot.ui.presentation.details_establishment.services.components.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.student.R

@Composable
fun HeaderImage(imageHeight: Dp) {
    Box(modifier = Modifier.fillMaxWidth().height(imageHeight)) {
        Image(
            painter = painterResource(id = R.drawable.bar_image),
            contentDescription = "Imagem do estabelecimento",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
}