package com.example.spot.ui.presentation.evaluate.model

sealed class EvaluateState{
    data object Idle : EvaluateState()
    data object Loading : EvaluateState()
    data class Error(val message: String) : EvaluateState()
    data object Success : EvaluateState()
}
