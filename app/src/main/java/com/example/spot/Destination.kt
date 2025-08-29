package com.example.spot

import androidx.annotation.DrawableRes
import com.student.R

enum class Destination(
    val route: String,
    val label: String,
    @DrawableRes val lightIcon: Int,
    @DrawableRes val darkIcon: Int,
    @DrawableRes val lightIconSelected: Int,
    @DrawableRes val darkIconSelected: Int
) {
    HOME(
        "home",
        "Início",
        R.drawable.home_light,
        R.drawable.home_dark,
        R.drawable.home_light_filled,
        R.drawable.home_dark_filled
    ),
    SAVE(
        "save",
        "Salvos",
        R.drawable.save_light,
        R.drawable.save_dark,
        R.drawable.save_light_filled,
        R.drawable.save_dark_filled
    ),
    CALENDAR(
        "calendar",
        "Agendamentos",
        R.drawable.calendar_light,
        R.drawable.calendar_dark,
        R.drawable.calendar_light_filled,
        R.drawable.calendar_dark_filled
    ),
    ACCOUNT(
        "account",
        "Perfil",
        R.drawable.account_light,
        R.drawable.account_dark,
        R.drawable.account_light_filled,
        R.drawable.account_dark_filled
    );
}
