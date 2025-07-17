package com.aaditx23.v2_assessment.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.aaditx23.v2_assessment.data.local.room.Answer.AnswerDao
import com.aaditx23.v2_assessment.data.local.room.Answer.AnswerEntity
import com.aaditx23.v2_assessment.data.local.room.AppDatabase
import com.aaditx23.v2_assessment.data.local.room.Submission.SubmissionDao
import com.aaditx23.v2_assessment.data.local.room.Submission.SubmissionEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {
    private lateinit var db: AppDatabase
    private lateinit var submissionDao: SubmissionDao
    private lateinit var answerDao: AnswerDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        submissionDao = db.submissionDao()
        answerDao = db.answerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testSubmissionAndAnswerFlow() = runBlocking {
        // 1. Create submission and test SubmissionDao methods
        val submission = SubmissionEntity(timestamp = System.currentTimeMillis())
        val submissionId = submissionDao.insertSubmission(submission)
        println("Inserted submission with id: $submissionId")

        val allSubmissions = submissionDao.getAllSubmissions()
        println("All submissions: $allSubmissions")

        val byId = submissionDao.getSubmissionById(submissionId)
        println("Submission by id: $byId")

        // 2. Test AnswerDao using this submissionId
        testAnswerFlow(submissionId)

        // 3. Delete submission after answer test
        submissionDao.deleteSubmissionById(submissionId)
        val afterDelete = submissionDao.getAllSubmissions()
        println("All submissions after delete: $afterDelete")
    }

    private suspend fun testAnswerFlow(submissionId: Long) {
        // Insert answer
        val answer = AnswerEntity(
            submissionId = submissionId,
            questionId = "q1",
            questionType = "text",
            answerValue = "42",
            answerOptionId = null
        )
        answerDao.insertAnswers(listOf(answer))
        println("Inserted answer: $answer")

        // Get all answers by submissionId
        val allBySubmission = answerDao.getAnswersBySubmissionId(submissionId)
        println("All answers by submissionId: $allBySubmission")

        // Get by id (get the id from the inserted answer)
        val insertedId = allBySubmission.firstOrNull()?.id?.toLong() ?: -1L
        val byId = answerDao.getAnswerById(insertedId)
        println("Answer by id: $byId")

        // Delete answer
        if (insertedId != -1L) {
            answerDao.deleteAnswerById(insertedId)
            val afterDelete = answerDao.getAnswersBySubmissionId(submissionId)
            println("All answers after delete: $afterDelete")
        }
    }
}