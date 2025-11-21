package com.example.spot.data.remote.dtos

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.spot.data.ACCESS_TOKEN_KEY
import com.example.spot.data.IS_DARK_MODE
import com.example.spot.data.REFRESH_TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    val accessToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN_KEY] ?: ""
    }

    val refreshToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN_KEY] ?: ""
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun clearTokens() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

    val isDarkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }

    suspend fun saveThemePreference(isDarkMode: Boolean){
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDarkMode
        }
    }
}