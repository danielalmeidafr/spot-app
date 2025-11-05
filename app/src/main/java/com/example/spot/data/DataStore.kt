package com.example.spot.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token_key")
val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token_key")
