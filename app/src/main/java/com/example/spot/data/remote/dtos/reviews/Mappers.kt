package com.example.spot.data.remote.dtos.reviews

import com.example.spot.ui.presentation.details_establishment.screens.reviews.model.ReviewUiModel
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ReviewResponse.toUiList(): List<ReviewUiModel> {
    return this.reviews.map { it.toUiModel() }
}

fun Review.toUiModel(): ReviewUiModel {
    return ReviewUiModel(
        userName = this.customer.nickName,
        userImage = this.customer.profileImage,
        score = this.score,
        comment = this.comment,
        formattedDate = formatDate(this.reviewedAt)
    )
}

private fun formatDate(dateString: String): String {
    return try {
        val dateTime = java.time.LocalDateTime.parse(
            dateString,
            DateTimeFormatter.ISO_DATE_TIME
        )

        dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (e: Exception) {
        dateString
    }
}