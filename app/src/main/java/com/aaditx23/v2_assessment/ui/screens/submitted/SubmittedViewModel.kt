package com.aaditx23.v2_assessment.ui.screens.submitted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaditx23.v2_assessment.data.repository.AnswerRepository
import com.aaditx23.v2_assessment.data.repository.RecordRepository
import com.aaditx23.v2_assessment.data.repository.SubmissionRepository
import com.aaditx23.v2_assessment.model.record.Record
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SubmittedViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val submissionRepository: SubmissionRepository,
    private val answerRepository: AnswerRepository
): ViewModel() {
    private val _submittedScreenUiState = MutableStateFlow<SubmissionScreenState>(SubmissionScreenState.NoSubmission)
    val submittedScreenUiState: StateFlow<SubmissionScreenState> = _submittedScreenUiState

    private val _records = MutableStateFlow<List<Record>>(listOf())
    val records: StateFlow<List<Record>> = _records


    fun fetchRecords(){
        viewModelScope.launch {
            try{
                val response = recordRepository.getRecords()
                _records.value = response.record
            }
            catch (e: Exception){
                _submittedScreenUiState.value = SubmissionScreenState.Error("Records could not be fetched")

            }
        }
    }

    fun getAllSubmissions(){
        viewModelScope.launch {
            val submissions = submissionRepository.getAllSubmissions()
            _submittedScreenUiState.value =
                if (submissions.isNotEmpty()) SubmissionScreenState.SubmissionFound(submissions)
                else SubmissionScreenState.NoSubmission
        }
    }

    fun refresh(){
        fetchRecords()
        getAllSubmissions()
    }
}