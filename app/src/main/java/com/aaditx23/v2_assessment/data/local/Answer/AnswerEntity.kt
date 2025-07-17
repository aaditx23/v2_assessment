package com.aaditx23.v2_assessment.data.local.Answer

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.aaditx23.v2_assessment.data.local.Submission.SubmissionEntity
import com.aaditx23.v2_assessment.model.Answer

@Entity(
    tableName = "answers",
    foreignKeys = [
        ForeignKey(
            entity = SubmissionEntity::class,
            parentColumns = ["id"],
            childColumns = ["submissionId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val submissionId: Long,
    val questionId: String,
    val questionType: String,
    val answerValue: String,
    val answerOptionId: String? = null
){
    fun toAnswer(): Answer{
        return Answer(
            questionType = questionType,
            referTo = null,
            value = answerValue,
            valueId = answerOptionId,
            hasError = false
        )
    }
}