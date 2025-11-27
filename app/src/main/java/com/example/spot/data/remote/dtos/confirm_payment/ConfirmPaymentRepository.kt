package com.example.spot.data.remote.dtos.confirm_payment

import com.example.spot.data.remote.network.SpotApiService

class ConfirmPaymentRepository(
    private val api: SpotApiService
) {
    suspend fun confirmPayment(request: ConfirmPaymentRequest): ConfirmPaymentResponse {
        return api.confirmPayment(request)
    }
}