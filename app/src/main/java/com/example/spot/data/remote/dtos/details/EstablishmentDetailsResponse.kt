package com.example.spot.data.remote.dtos.details

data class EstablishmentDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val address: AddressResponse,
    val openingDates: List<OpeningDatesResponse>,
    val averageRating: Double,
    val paymentMethods: List<PaymentMethodsResponse>,
    val offeredServices: List<OfferedServicesResponse>,
    val attendants: List<Attendants>,
    val imageUrls: List<String>,
    val type: String = "BARBERSHOP"
)