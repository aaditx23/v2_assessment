package com.aaditx23.v2_assessment.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.aaditx23.v2_assessment.data.local.AppDatabase
import com.aaditx23.v2_assessment.data.local.Submission.SubmissionEntity
import com.aaditx23.v2_assessment.data.local.Answer.AnswerEntity
import com.aaditx23.v2_assessment.data.repository.SubmissionRepository
import com.aaditx23.v2_assessment.data.repository.AnswerRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class RepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var submissionRepository: SubmissionRepository
    private lateinit var answerRepository: AnswerRepository

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        submissionRepository = SubmissionRepository(db.submissionDao())
        answerRepository = AnswerRepository(db.answerDao())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testSubmissionAndAnswerRepositoryFlow() = runBlocking {
        // 1. Create submission and test SubmissionRepository methods
        val submissionId = submissionRepository.insertSubmission()
        println("Inserted submission with id: $submissionId")

        val allSubmissions = submissionRepository.getAllSubmissions()
        println("All submissions: $allSubmissions")

        val byId = submissionRepository.getSubmissionById(submissionId)
        println("Submission by id: $byId")

        // 2. Test AnswerRepository using this submissionId
        testAnswerRepositoryFlow(submissionId)

        // 3. Delete submission after answer test
        submissionRepository.deleteSubmissionById(submissionId)
        val afterDelete = submissionRepository.getAllSubmissions()
        println("All submissions after delete: $afterDelete")
    }

    private suspend fun testAnswerRepositoryFlow(submissionId: Long) {
        // Insert answer
        val answer = AnswerEntity(
            submissionId = submissionId,
            questionId = "q1",
            questionType = "text",
            answerValue = "42",
            answerOptionId = null
        )
        answerRepository.insertAnswers(listOf(answer))
        println("Inserted answer: $answer")

        // Get all answers by submissionId
        val allBySubmission = answerRepository.getAnswersBySubmissionId(submissionId)
        println("All answers by submissionId: $allBySubmission")

        // Get by id (get the id from the inserted answer)
        val insertedId = allBySubmission.firstOrNull()?.id?.toLong() ?: -1L
        val byId = answerRepository.getAnswerById(insertedId)
        println("Answer by id: $byId")

        // Delete answer
        if (insertedId != -1L) {
            answerRepository.deleteAnswerById(insertedId)
            val afterDelete = answerRepository.getAnswersBySubmissionId(submissionId)
            println("All answers after delete: $afterDelete")
        }
    }
}