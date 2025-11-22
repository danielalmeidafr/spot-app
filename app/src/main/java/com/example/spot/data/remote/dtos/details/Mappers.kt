@file:Suppress("DEPRECATION")

package com.example.spot.data.remote.dtos.details

import com.example.spot.ui.presentation.details_establishment.screens.services.model.EstablishmentDetailsData
import com.example.spot.ui.presentation.details_establishment.screens.services.model.OfferedServiceCategoryData
import com.example.spot.ui.presentation.details_establishment.screens.services.model.OfferedServiceData
import java.util.Locale

fun EstablishmentDetailsResponse.toEstablishmentDetailsData(): EstablishmentDetailsData{
    return EstablishmentDetailsData(
        title = this.name,
        location = "${this.address.street}, ${this.address.number} - ${this.address.neighborhood}, ${this.address.city} - ${this.address.state}, ${this.address.zipCode}"
    )
}

fun List<OfferedServicesResponse>.toOfferedServicesCategoryData(): List<OfferedServiceCategoryData>{
    val grouped = this.groupBy { it.category }

    return grouped.map{ (category, services) ->
        OfferedServiceCategoryData(
            title = category(category),
            services = services.map { it.toOfferedServicesData() }
        )
    }
}

fun OfferedServicesResponse.toOfferedServicesData(): OfferedServiceData{
    return OfferedServiceData(
        title = this.name,
        description = this.description,
        price = String.format(Locale("pt", "BR"), "%.2f", this.price)
    )
}

fun List<PaymentMethodsResponse>.toPaymentMethods(): List<PaymentMethods>{
    return this.map { it.type }
}

private fun category(category: OfferedServiceCategory): String{
    return when (category){
        OfferedServiceCategory.CUT -> "Cortes"
        OfferedServiceCategory.PROCESS -> "Processos"
        OfferedServiceCategory.FINISH -> "Acabamentos"
        OfferedServiceCategory.COMBO -> "Combos"
    }
}