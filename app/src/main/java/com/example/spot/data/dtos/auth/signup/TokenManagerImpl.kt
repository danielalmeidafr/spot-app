package com.example.spot.data.dtos.auth.signup

class TokenManagerImpl : TokenManager {

    private var accessToken: String? = null
    private var refreshToken: String? = null

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        println("Tokens salvos: access=$accessToken, refresh=$refreshToken")
    }

    fun getAccessToken(): String? = accessToken
    fun getRefreshToken(): String? = refreshToken
}