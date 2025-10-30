package com.example.spot.data.dtos.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.spot.data.AUTH_KEY
import com.example.spot.data.dtos.auth.login.SignInRequest
import com.example.spot.data.dtos.auth.login.SignInResponse
import com.example.spot.data.dtos.auth.signup.SignUpRequest
import com.example.spot.data.dtos.auth.signup.SignUpResponse
import com.example.spot.data.network.SpotApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val api: SpotApiService,
    private val dataStore: DataStore<Preferences>
) {
    suspend fun signIn(
        request: SignInRequest
    ) : SignInResponse {
        return api.signIn(request)
    }

    suspend fun signUp(
        request: SignUpRequest
    ) : SignUpResponse {
        return api.signUp(request)
    }

    val token: Flow<String> = dataStore.data.map { preferences ->
        preferences[AUTH_KEY] ?: ""
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_KEY)
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_KEY] = token
        }
    }
}