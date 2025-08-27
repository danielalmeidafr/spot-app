package com.example.spot.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.student.R

val Montserrat = FontFamily(
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.montserrat_light, weight = FontWeight.Light),
    Font(R.font.montserrat_lightitalic, weight = FontWeight.Light, FontStyle.Italic),
    Font(R.font.montserrat_medium, weight = FontWeight.Medium),
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
    Font(R.font.montserrat_semibold, weight = FontWeight.SemiBold),
    Font(R.font.montserrat_semibolditalic, weight = FontWeight.SemiBold, FontStyle.Italic)
)
val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp
    ),
)


