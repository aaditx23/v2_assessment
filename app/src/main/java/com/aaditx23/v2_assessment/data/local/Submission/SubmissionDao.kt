package com.aaditx23.v2_assessment.data.local.Submission

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aaditx23.v2_assessment.data.local.Submission.SubmissionEntity

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

    @Query("DELETE FROM submissions")
    suspend fun deleteAllSubmissions()

    @Query("SELECT COUNT(*) FROM submissions")
    suspend fun getSubmissionCount(): Int


}


