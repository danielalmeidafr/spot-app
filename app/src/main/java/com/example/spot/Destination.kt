package com.example.spot

import androidx.annotation.DrawableRes
import com.student.R

enum class Destination(
    val route: String,
    val label: String,
    @DrawableRes val lightIcon: Int,
    @DrawableRes val darkIcon: Int
) {
    HOME("home", "Início", R.drawable.home_light, R.drawable.home_dark),
    SAVE("save", "Salvos", R.drawable.save_light, R.drawable.save_dark),
    CALENDAR("calendar", "Agendamentos", R.drawable.calendar_light, R.drawable.calendar_dark),
    ACCOUNT("account", "Perfil", R.drawable.account_light, R.drawable.account_dark);
}
