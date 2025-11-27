package com.example.spot.ui.presentation.details_establishment.screens.reviews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.reviews.ReviewRepository
import com.example.spot.data.remote.dtos.reviews.toUiList
import com.example.spot.ui.presentation.details_establishment.screens.reviews.model.ReviewsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ReviewsViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ReviewsState>(ReviewsState.Loading)
    val state = _state.asStateFlow()

    fun loadReviews(establishmentId: String) {
        viewModelScope.launch {
            _state.update { ReviewsState.Loading }
            try {
                val response = reviewRepository.getReviews("recent", establishmentId)

                _state.update {
                    ReviewsState.Success(
                        averageScore = response.averageScore,
                        totalReviews = response.totalReviews,
                        reviews = response.toUiList()
                    )
                }
            } catch (e: IOException) {
                _state.update { ReviewsState.Error("Falha na conexão. Verifique sua internet.") }
            } catch (e: HttpException) {
                _state.update { ReviewsState.Error("Erro no servidor: ${e.message()}") }
            } catch (e: Exception) {
                _state.update { ReviewsState.Error("Erro desconhecido: ${e.message}") }
            }
        }
    }
}