package com.aaditx23.v2_assessment.data.repository

import com.aaditx23.v2_assessment.data.local.Answer.AnswerDao
import com.aaditx23.v2_assessment.data.local.Answer.AnswerEntity
import javax.inject.Inject

class AnswerRepository @Inject constructor(
    private val answerDao: AnswerDao
) {
    suspend fun insertAnswers(answers: List<AnswerEntity>) =
        answerDao.insertAnswers(answers)

    suspend fun getAnswersBySubmissionId(submissionId: Long): List<AnswerEntity> =
        answerDao.getAnswersBySubmissionId(submissionId)

    suspend fun getAnswerById(id: Long): AnswerEntity? =
        answerDao.getAnswerById(id)

    suspend fun deleteAnswerById(id: Long) =
        answerDao.deleteAnswerById(id)
}