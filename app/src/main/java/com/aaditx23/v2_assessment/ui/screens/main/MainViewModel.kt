package com.aaditx23.v2_assessment.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaditx23.v2_assessment.data.local.Answer.AnswerEntity
import com.aaditx23.v2_assessment.data.repository.AnswerRepository

import com.aaditx23.v2_assessment.data.repository.RecordRepository
import com.aaditx23.v2_assessment.data.repository.SubmissionRepository
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.ReferId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.aaditx23.v2_assessment.ui.components.JsonFlow.states.AnswerState
import com.aaditx23.v2_assessment.ui.components.JsonFlow.states.RecordUiState


@HiltViewModel
class MainViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val submissionRepository: SubmissionRepository,
    private val answerRepository: AnswerRepository
) : ViewModel() {
    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state: StateFlow<MainScreenState> = _state

    private val _answerState = MutableStateFlow(AnswerState())
    val answerState: StateFlow<AnswerState> = _answerState

    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState: StateFlow<RecordUiState> = _uiState


    fun updateAnswer(questionId: String, answer: Answer, referTo: ReferId?) {

        val current = _answerState.value
        current.addAnswer(questionId, answer)
        _answerState.value = current
        println("Updated $questionId to \n$answer")
        navigateNext(referTo)

    }
    fun navigateNext(referTo: ReferId?) {
        val nextId = referTo?.id ?: uiState.value.nextId
        if (nextId == "submit") return
        else if (nextId.isNotEmpty() && nextId != uiState.value.currentId) {
            setCurrentId(nextId)
        }
    }

    fun clearAnswers() {
        _answerState.value.clear()
    }

    fun fetchRecords() {
        viewModelScope.launch {
            _state.value = MainScreenState.Loading
            try {
                val response = recordRepository.getRecords().record
                _state.value = MainScreenState.Success(response)
            } catch (e: Exception) {
                _state.value = MainScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setCurrentId(id: String) {
        _uiState.value = _uiState.value.copy(currentId = id)
    }

    fun setSubmitted(isSubmitted: Boolean){
        _uiState.value = _uiState.value.copy(submitted = isSubmitted)
    }

    fun updateValueErrorAndNextId(ans: Answer) {
        _uiState.value = _uiState.value.copy(
            hasValue = ans.value.isNotEmpty(),
            hasError = ans.hasError,
            nextId = ans.referTo?.id ?: _uiState.value.nextId
        )
    }

    fun resetErrorAndHasValue() {
        _uiState.value = _uiState.value.copy(
            hasValue = false,
            hasError = false,
            submitted = false
        )
    }
    fun restart(){
        setCurrentId("1")
        clearAnswers()
    }

    fun submitAnswer(questionId: String, answer: Answer){
        val current = _answerState.value
        current.addAnswer(questionId, answer)
        _answerState.value = current

        viewModelScope.launch {
            val submissionId = submissionRepository.insertSubmission()
            val answerList = answerState.value.answers.entries.map { (questionId, ans)->
                ans.toEntity(submissionId, questionId)
            }
            answerRepository.insertAnswers(answerList)
            setSubmitted(true)
        }

    }

}
