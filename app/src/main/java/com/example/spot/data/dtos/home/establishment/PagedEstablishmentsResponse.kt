package com.example.spot.data.dtos.home.establishment

data class PagedEstablishmentsResponse(
    val establishments: List<EstablishmentDto>,
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Int
)