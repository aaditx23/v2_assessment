package com.aaditx23.v2_assessment.ui.screens.submitted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaditx23.v2_assessment.data.repository.RecordRepository
import com.aaditx23.v2_assessment.data.repository.SubmissionRepository
import com.aaditx23.v2_assessment.model.record.Record
import com.aaditx23.v2_assessment.util.FileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SubmittedViewModel @Inject constructor(
    private val submissionRepository: SubmissionRepository,
): ViewModel() {
    private val _submittedScreenState = MutableStateFlow<SubmissionScreenState>(SubmissionScreenState.NoSubmission)
    val submittedScreenState: StateFlow<SubmissionScreenState> = _submittedScreenState

    private val _imagePath = MutableStateFlow<String>("")
    val imagePath: StateFlow<String> = _imagePath


    fun getAllSubmissions(){
        viewModelScope.launch {
            val submissions = submissionRepository.getAllSubmissions()
            _submittedScreenState.value =
                if (submissions.isNotEmpty()) SubmissionScreenState.SubmissionFound(submissions)
                else SubmissionScreenState.NoSubmission
        }
    }

    fun getImagePath(id: Long){
        viewModelScope.launch {
            _imagePath.value = submissionRepository.getImagePath(id)
        }
    }

    fun deleteSubmission(id: Long) {
        viewModelScope.launch {
            _submittedScreenState.value = SubmissionScreenState.Loading
            try {
                submissionRepository.deleteSubmissionById(id)
                println("DELETED: $id")
                getAllSubmissions()
            } catch (e: Exception) {
                _submittedScreenState.value = SubmissionScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }
}