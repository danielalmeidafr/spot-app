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
                val wrapper =
                    establishmentDetailsRepository.getEstablishmentDetailsById(establishmentId)

                val response = wrapper.establishment

                val headerData = response.toEstablishmentDetailsData()
                val servicesData = response.offeredServices.toOfferedServicesCategoryData()

                _state.update {
                    DetailsState.Success(
                        header = headerData,
                        services = servicesData
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
        viewModelScope.launch {
            try {
                establishmentDetailsRepository.favorite(establishmentId)
            } catch (e: IOException) {
                _state.update { DetailsState.Error("Falha na conexão. Verifique sua internet.") }
            } catch (e: HttpException) {
                _state.update { DetailsState.Error("Erro no servidor: ${e.message()}") }
            } catch (e: Exception) {
                _state.update { DetailsState.Error("Erro desconhecido: ${e.message}") }
            }
        }
    }

    fun unfavorite(establishmentId: String) {
        viewModelScope.launch {
            try {
                establishmentDetailsRepository.unfavorite(establishmentId)
            } catch (e: IOException) {
                _state.update { DetailsState.Error("Falha na conexão. Verifique sua internet.") }
            } catch (e: HttpException) {
                _state.update { DetailsState.Error("Erro no servidor: ${e.message()}") }
            } catch (e: Exception) {
                _state.update { DetailsState.Error("Erro desconhecido: ${e.message}") }
            }
        }
    }
}
