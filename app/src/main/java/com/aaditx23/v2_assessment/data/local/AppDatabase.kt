package com.aaditx23.v2_assessment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aaditx23.v2_assessment.data.local.Answer.AnswerDao
import com.aaditx23.v2_assessment.data.local.Answer.AnswerEntity
import com.aaditx23.v2_assessment.data.local.Submission.SubmissionDao
import com.aaditx23.v2_assessment.data.local.Submission.SubmissionEntity

@Database(
    entities = [
        SubmissionEntity::class,
        AnswerEntity::class
       ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDao
    abstract fun submissionDao(): SubmissionDao
}