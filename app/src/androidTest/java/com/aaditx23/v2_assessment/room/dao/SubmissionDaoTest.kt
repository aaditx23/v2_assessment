package com.aaditx23.v2_assessment.room.dao

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.aaditx23.v2_assessment.data.local.Submission.SubmissionDao
import com.aaditx23.v2_assessment.data.local.Submission.SubmissionEntity
import com.aaditx23.v2_assessment.data.local.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class SubmissionDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: SubmissionDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.submissionDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testSubmissionFlow() = runBlocking {
        // 1. Insert submission
        val submission = SubmissionEntity(timestamp = System.currentTimeMillis())

        val id = dao.insertSubmission(submission)
        println("Inserted submission with id: $id")

        // 2. Get all submissions
        val all = dao.getAllSubmissions()
        println("All submissions: $all")

        // 3. Get by id
        val byId = dao.getSubmissionById(id)
        println("Submission by id: $byId")

        // 4. Delete
        dao.deleteSubmissionById(id)
        val afterDelete = dao.getAllSubmissions()
        println("All submissions after delete: $afterDelete")
    }
}