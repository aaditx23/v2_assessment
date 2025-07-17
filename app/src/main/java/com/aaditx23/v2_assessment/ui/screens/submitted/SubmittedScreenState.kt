package com.aaditx23.v2_assessment.ui.screens.submitted

import com.aaditx23.v2_assessment.data.local.room.Submission.SubmissionEntity

sealed class SubmissionScreenState {
    object Loading: SubmissionScreenState()
    object NoSubmission : SubmissionScreenState()
    data class SubmissionFound(val submissions: List<SubmissionEntity>) : SubmissionScreenState()
    data class Error(val error: String) : SubmissionScreenState()
}