package com.aaditx23.v2_assessment.data.local.room.Submission

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SubmissionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: SubmissionEntity): Long

    @Query("SELECT * FROM submissions ORDER BY timestamp DESC")
    suspend fun getAllSubmissions(): List<SubmissionEntity>

    @Query("SELECT * FROM submissions WHERE id = :id")
    suspend fun getSubmissionById(id: Long): SubmissionEntity?

    @Query("DELETE FROM submissions WHERE id = :id")
    suspend fun deleteSubmissionById(id: Long)

    @Query("SELECT answerValue FROM answers WHERE questionType = 'camera' AND questionId = '5' AND submissionId = :id")
    suspend fun getImagePathFromSubmission(id: Long): String

    @Query("DELETE FROM submissions")
    suspend fun deleteAllSubmissions()

    @Query("SELECT COUNT(*) FROM submissions")
    suspend fun getSubmissionCount(): Int


}


