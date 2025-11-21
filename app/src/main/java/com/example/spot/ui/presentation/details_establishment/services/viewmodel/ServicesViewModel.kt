package com.example.spot.ui.presentation.details_establishment.services.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.ui.presentation.details_establishment.services.model.EstablishmentData
import com.example.spot.ui.presentation.details_establishment.services.model.ServiceCategoryData
import com.example.spot.ui.presentation.details_establishment.services.model.ServiceData
import com.example.spot.ui.presentation.details_establishment.services.model.ServiceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class ServicesViewModel : ViewModel() {

    private val _state = MutableStateFlow<ServiceState>(ServiceState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val establishmentInfo = fetchInfoEstablishment()
                val services = fetchServices()

                _state.update {
                    ServiceState.Success(
                        title = establishmentInfo.title,
                        location = establishmentInfo.location,
                        servicesData = services
                    )
                }
            } catch (e: IOException) {
                _state.update { ServiceState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                val message = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    val json = JSONObject(errorBody ?: "")

                    json.optString("message", "Erro no servidor")
                } catch (_: Exception) {
                    "Erro no servidor"
                }
                _state.update { ServiceState.Error(message) }
            } catch (e: Exception) {
                _state.update { ServiceState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    private fun fetchServices(): List<ServiceCategoryData> {
        return listOf(
            ServiceCategoryData(
                title = "Cortes",
                services = listOf(
                    ServiceData(
                        title = "Corte simples",
                        description = "Corte social na tesoura",
                        price = "35,00"
                    ),
                    ServiceData(
                        title = "Corte com lavagem",
                        description = "Corte social com lavagem e finalização",
                        price = "45,00"
                    ),
                    ServiceData(
                        title = "Degradê (Fade)",
                        description = "Corte com efeito degradê nas laterais",
                        price = "40,00"
                    )
                )
            ),
            ServiceCategoryData(
                title = "Barba",
                services = listOf(
                    ServiceData(
                        title = "Barba simples",
                        description = "Modelagem e acabamento da barba",
                        price = "30,00"
                    ),
                    ServiceData(
                        title = "Barba e toalha quente",
                        description = "Barboterapia completa com toalha quente e massagem",
                        price = "40,00"
                    )
                )
            ),
            ServiceCategoryData(
                title = "Combo",
                services = listOf(
                    ServiceData(
                        title = "Corte + Barba",
                        description = "Corte de cabelo e barba completa",
                        price = "65,00"
                    ),
                    ServiceData(
                        title = "Combo Premium",
                        description = "Corte com lavagem, barba com toalha quente e sobrancelha",
                        price = "90,00"
                    )
                )
            ),
            ServiceCategoryData(
                title = "Outros",
                services = listOf(
                    ServiceData(
                        title = "Sobrancelha",
                        description = "Design e limpeza das sobrancelhas",
                        price = "15,00"
                    ),
                    ServiceData(
                        title = "Pigmentação de cabelo",
                        description = "Cobertura de fios brancos ou pigmentação de risco",
                        price = "50,00"
                    )
                )
            )
        )
    }

    private fun fetchInfoEstablishment(): EstablishmentData{
        return EstablishmentData(
            title = "Barbearia Corleone",
            location = "Av. Brigadeiro Faria Lima, 1425 - Loja B2 - Jardim Paulistano, São Paulo - SP, 01452-002"
        )
    }
}