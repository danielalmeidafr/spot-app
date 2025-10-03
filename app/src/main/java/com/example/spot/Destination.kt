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
        R.drawable.home_filled_light,
        R.drawable.home_filled_dark
    ),
    SAVE(
        "save",
        "Favoritos",
        R.drawable.save_light,
        R.drawable.save_dark,
        R.drawable.save_filled_light,
        R.drawable.save_filled_dark
    ),
    CALENDAR(
        "calendar",
        "Agenda",
        R.drawable.calendar_light,
        R.drawable.calendar_dark,
        R.drawable.calendar_filled_light,
        R.drawable.calendar_filled_dark
    ),
    PROFILE(
        "profile",
        "Perfil",
        R.drawable.profile_light,
        R.drawable.profile_dark,
        R.drawable.profile_filled_dark,
        R.drawable.profile_filled_light
    );
}
