package com.aaditx23.v2_assessment.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aaditx23.v2_assessment.data.local.room.Answer.AnswerDao
import com.aaditx23.v2_assessment.data.local.room.Answer.AnswerEntity
import com.aaditx23.v2_assessment.data.local.room.Submission.SubmissionDao
import com.aaditx23.v2_assessment.data.local.room.Submission.SubmissionEntity

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