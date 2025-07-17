package com.aaditx23.v2_assessment.ui.screens.answer

import com.aaditx23.v2_assessment.data.local.Answer.AnswerEntity

sealed class AnswerScreenState {
    object Loading : AnswerScreenState()
    data class ShowAnswers(val answers: List<AnswerEntity>) : AnswerScreenState()
    data class Error(val error: String) : AnswerScreenState()
}