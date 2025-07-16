package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow.states

import com.aaditx23.v2_assessment.model.Answer

data class AnswerState(
    val answers: MutableMap<String, Answer> = mutableMapOf()
) {
    fun addAnswer(questionId: String, answer: Answer) {
        answers[questionId] = answer
    }

    fun clear() = answers.clear()
}