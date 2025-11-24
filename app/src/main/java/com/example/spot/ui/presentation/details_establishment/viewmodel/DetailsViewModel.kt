package com.example.spot.ui.presentation.details_establishment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.details.EstablishmentDetailsRepository
import com.example.spot.data.remote.dtos.details.toEstablishmentDetailsData
import com.example.spot.data.remote.dtos.details.toOfferedServicesCategoryData
import com.example.spot.ui.presentation.details_establishment.model.DetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailsViewModel(
    private val establishmentDetailsRepository: EstablishmentDetailsRepository
) : ViewModel() {
    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val state = _state.asStateFlow()

    fun loadEstablishment(establishmentId: String) {
        viewModelScope.launch {
            _state.update { DetailsState.Loading }

            try {
                val wrapper = establishmentDetailsRepository.getEstablishmentDetailsById(establishmentId)
                val response = wrapper.establishment

                val headerData = response.toEstablishmentDetailsData()
                val servicesData = response.offeredServices.toOfferedServicesCategoryData()
                val isFavorite = wrapper.isFavorite
                val imageUrls = response.imageUrls

                _state.update {
                    DetailsState.Success(
                        header = headerData,
                        services = servicesData,
                        isFavorite = isFavorite,
                        imageUrls = imageUrls
                    )
                }

            } catch (e: IOException) {
                _state.update { DetailsState.Error("Falha na conexão. Verifique sua internet.") }
            } catch (e: HttpException) {
                _state.update { DetailsState.Error("Erro no servidor: ${e.message()}") }
            } catch (e: Exception) {
                _state.update { DetailsState.Error("Erro desconhecido: ${e.message}") }
            }
        }
    }

    fun favorite(establishmentId: String) {
        updateFavoriteState(true)

        viewModelScope.launch {
            try {
                establishmentDetailsRepository.favorite(establishmentId)
            } catch (e: HttpException) {
                if (e.code() == 403) {
                    updateFavoriteState(false)
                    setLoginDialogVisibility(true)
                } else {
                    updateFavoriteState(false)
                }
            } catch (e: Exception) {
                updateFavoriteState(false)
            }
        }
    }

    fun unfavorite(establishmentId: String) {
        updateFavoriteState(false)

        viewModelScope.launch {
            try {
                establishmentDetailsRepository.unfavorite(establishmentId)
            } catch (e: HttpException) {
                if (e.code() == 403) {
                    updateFavoriteState(true)
                    setLoginDialogVisibility(true)
                } else {
                    updateFavoriteState(true)
                }
            } catch (e: Exception) {
                updateFavoriteState(true)
            }
        }
    }

    fun dismissLoginDialog() {
        setLoginDialogVisibility(false)
    }

    private fun setLoginDialogVisibility(show: Boolean) {
        _state.update { currentState ->
            if (currentState is DetailsState.Success) {
                currentState.copy(showLoginDialog = show)
            } else {
                currentState
            }
        }
    }

    private fun updateFavoriteState(isFavorite: Boolean) {
        _state.update { currentState ->
            if (currentState is DetailsState.Success) {
                currentState.copy(isFavorite = isFavorite)
            } else {
                currentState
            }
        }
    }
}
