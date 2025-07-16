package com.aaditx23.v2_assessment.data.repository

import com.aaditx23.v2_assessment.data.local.Submission.SubmissionDao
import com.aaditx23.v2_assessment.data.local.Submission.SubmissionEntity
import javax.inject.Inject

class SubmissionRepository @Inject constructor(
    private val submissionDao: SubmissionDao
) {
    suspend fun insertSubmission(submission: SubmissionEntity): Long =
        submissionDao.insertSubmission(submission)

    suspend fun getAllSubmissions(): List<SubmissionEntity> =
        submissionDao.getAllSubmissions()

    suspend fun getSubmissionById(id: Long): SubmissionEntity? =
        submissionDao.getSubmissionById(id)

    suspend fun deleteSubmissionById(id: Long) =
        submissionDao.deleteSubmissionById(id)
}