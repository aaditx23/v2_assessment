package com.aaditx23.v2_assessment.data.local.room.Answer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswers(answers: List<AnswerEntity>)

    @Query("SELECT * FROM answers where submissionId = :submissionId")
    suspend fun getAnswersBySubmissionId(submissionId: Long): List<AnswerEntity>

    @Query("SELECT * FROM answers WHERE id = :id")
        suspend fun getAnswerById(id: Long): AnswerEntity?

    @Query("DELETE FROM answers WHERE id = :id")
        suspend fun deleteAnswerById(id: Long)


}
