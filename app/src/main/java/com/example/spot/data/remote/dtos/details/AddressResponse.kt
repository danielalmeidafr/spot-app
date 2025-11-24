package com.example.spot.data.remote.dtos.details

data class AddressResponse(
    val id: String,
    val street: String,
    val number: String,
    val neighborhood: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val complement: String? = null
)
