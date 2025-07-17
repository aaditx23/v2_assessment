package com.aaditx23.v2_assessment.data.repository

import com.aaditx23.v2_assessment.data.local.room.Submission.SubmissionDao
import com.aaditx23.v2_assessment.data.local.room.Submission.SubmissionEntity
import javax.inject.Inject

class SubmissionRepository @Inject constructor(
    private val submissionDao: SubmissionDao
) {
    suspend fun insertSubmission(): Long =
        submissionDao.insertSubmission(
            SubmissionEntity(timestamp = System.currentTimeMillis())
        )

    suspend fun getAllSubmissions(): List<SubmissionEntity> =
        submissionDao.getAllSubmissions()

    suspend fun getSubmissionById(id: Long): SubmissionEntity? =
        submissionDao.getSubmissionById(id)

    suspend fun deleteSubmissionById(id: Long) =
        submissionDao.deleteSubmissionById(id)

    suspend fun getSubmissionCount() =
        submissionDao.getSubmissionCount()
}