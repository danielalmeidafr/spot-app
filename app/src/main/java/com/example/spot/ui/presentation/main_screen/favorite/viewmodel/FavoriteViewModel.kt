package com.example.spot.ui.presentation.main_screen.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.UserPreferencesRepository
import com.example.spot.data.remote.dtos.favorite.FavoriteEstablishmentRepository
import com.example.spot.data.remote.dtos.favorite.toEstablishmentData
import com.example.spot.ui.presentation.main_screen.favorite.model.FavoriteState
import com.example.spot.ui.presentation.main_screen.home.model.EstablishmentData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

class FavoriteViewModel(
    private val favoriteEstablishmentRepository: FavoriteEstablishmentRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val state = _state.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private var userLocation: Pair<Double, Double>? = null

    init {
        viewModelScope.launch {
            userPreferencesRepository.accessToken.collect { token ->
                val logged = token.isNotEmpty()
                _isLoggedIn.value = logged

                try {
                    val establishments = fetchFavoritesEstablishments()

                    _state.update {
                        FavoriteState.Success(
                            establishmentsData = establishments
                        )
                    }
                } catch (e: IOException) {
                    _state.update { FavoriteState.Error("Falha na conexão") }
                } catch (e: HttpException){
                    if (e.code() == 403){
                        _isLoggedIn.value = false
                        return@collect
                    }
                    val message = parseErrorMessage(e)
                    _state.update { FavoriteState.Error(message) }
                } catch (e: Exception) {
                    _state.update { FavoriteState.Error("Ocorreu um erro: ${e.message}") }
                }
            }
        }
    }

    fun updateUserLocation(lat: Double, long: Double) {
        val newLocation = Pair(lat, long)

        if (userLocation == newLocation) return

        userLocation = newLocation

        if (_state.value is FavoriteState.Success) {
            viewModelScope.launch {
                reloadEstablishmentsWithLocation()
            }
        }
    }

    private suspend fun reloadEstablishmentsWithLocation() {
        try {
            val currentState = _state.value as? FavoriteState.Success ?: return

            val establishments = fetchFavoritesEstablishments()

            _state.update {
                currentState.copy(establishmentsData = establishments)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchFavoritesEstablishments(): List<EstablishmentData> {
        val favorites = favoriteEstablishmentRepository.getAllFavoritesEstablishments(
            customerLatitude = userLocation?.first,
            customerLongitude = userLocation?.second
        )

        return favorites.map { it.toEstablishmentData() }
    }

    private fun parseErrorMessage(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val json = JSONObject(errorBody ?: "")
            json.optString("message", "Erro no servidor")
        } catch (_: Exception) {
            "Erro no servidor"
        }
    }
}