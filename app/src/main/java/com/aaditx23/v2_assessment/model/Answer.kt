package com.aaditx23.v2_assessment.model

import com.aaditx23.v2_assessment.data.local.Answer.AnswerEntity
import com.aaditx23.v2_assessment.model.record.ReferId

data class Answer (
    val questionType: String,
    val referTo: ReferId?,
    val value: String,
    val valueId: String? = null,
    var hasError: Boolean = false
){
    fun toEntity(submissionId: Long, questionId: String): AnswerEntity{
        return AnswerEntity(
            submissionId = submissionId,
            questionId = questionId,
            questionType = questionType,
            answerValue = value,
            answerOptionId = valueId
        )
    }
}