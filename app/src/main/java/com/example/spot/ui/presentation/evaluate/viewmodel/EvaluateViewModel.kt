package com.example.spot.ui.presentation.evaluate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.evaluate.EvaluateRepository
import com.example.spot.data.remote.dtos.evaluate.RatingData
import com.example.spot.data.remote.dtos.evaluate.RatingRequest
import com.example.spot.ui.presentation.evaluate.model.EvaluateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

class EvaluateViewModel(
    private val evaluateRepository: EvaluateRepository
): ViewModel() {

    private val _state = MutableStateFlow<EvaluateState>(EvaluateState.Idle)
    val state = _state.asStateFlow()

    fun onEvaluateClicked(establishmentId: String, score: Int, comment: String){
        viewModelScope.launch {
            _state.update { EvaluateState.Loading }

            try {
                val rating = RatingData(score, comment)
                val request = RatingRequest(rating)

                evaluateRepository.evaluate(establishmentId, request)

                _state.update { EvaluateState.Success }
            } catch (e: IOException) {
                _state.update { EvaluateState.Error("Falha na conexão. Verifique sua internet.") }
            } catch (e: HttpException) {
                val message = parseErrorMessage(e)
                _state.update { EvaluateState.Error(message) }
            } catch (e: Exception) {
                _state.update { EvaluateState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }


    private fun parseErrorMessage(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val json = JSONObject(errorBody ?: "")
            json.optString("message", "Erro no servidor")
        } catch (_: Exception) {
            "Erro no servidor"
        }
    }
}