package com.aaditx23.v2_assessment.ui.screens.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import androidx.core.content.edit


@HiltViewModel
class MainViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val submissionRepository: SubmissionRepository,
    private val answerRepository: AnswerRepository
) : ViewModel() {
    private val _mainUiState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val mainUiState: StateFlow<MainScreenState> = _mainUiState

    private val _answerState = MutableStateFlow(AnswerState())
    val answerState: StateFlow<AnswerState> = _answerState

    private val _recordUiState = MutableStateFlow(RecordUiState())
    val recordUiState: StateFlow<RecordUiState> = _recordUiState

    private val _submitted = MutableStateFlow(false)
    val submitted: StateFlow<Boolean> = _submitted


    fun updateAnswer(questionId: String, answer: Answer, referTo: ReferId?) {

        val current = _answerState.value
        current.addAnswer(questionId, answer)
        _answerState.value = current
        println("Updated $questionId to \n$answer")
        navigateNext(referTo)

    }
    fun navigateNext(referTo: ReferId?) {
        val nextId = referTo?.id ?: recordUiState.value.nextId
        if (nextId == "submit") return
        else if (nextId.isNotEmpty() && nextId != recordUiState.value.currentId) {
            setCurrentId(nextId)
        }
    }

    fun clearAnswers() {
        _answerState.value.clear()
    }

    fun fetchRecords() {
        viewModelScope.launch {
            _mainUiState.value = MainScreenState.Loading
            try {
                val response = recordRepository.getRecords().record
                _mainUiState.value = MainScreenState.Success(response)
            } catch (e: Exception) {
                _mainUiState.value = MainScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setCurrentId(id: String) {
        _recordUiState.value = _recordUiState.value.copy(currentId = id)
    }


    fun updateValueErrorAndNextId(ans: Answer) {
        _recordUiState.value = _recordUiState.value.copy(
            hasValue = ans.value.isNotEmpty(),
            hasError = ans.hasError,
            nextId = ans.referTo?.id ?: _recordUiState.value.nextId
        )
    }

    fun resetErrorAndHasValue() {
        _recordUiState.value = _recordUiState.value.copy(
            hasValue = false,
            hasError = false,
        )
    }
    fun restart(){
        setCurrentId("1")
        clearAnswers()
    }

    fun submitAnswer(questionId: String, answer: Answer
    ){
        val current = _answerState.value
        current.addAnswer(questionId, answer)
        _answerState.value = current

        viewModelScope.launch {
            val submissionId = submissionRepository.insertSubmission()
            val answerList = answerState.value.answers.entries.map { (questionId, ans)->
                ans.toEntity(submissionId, questionId)
            }
            answerRepository.insertAnswers(answerList)
            _submitted.value = true
        }

    }
    fun setSubmitted(submitted: Boolean){
        _submitted.value = submitted
    }

    fun setSubmittedFlag(context: Context, isSubmitted: Boolean){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
            preferences.edit { putBoolean("submitted", isSubmitted) }

        }
    }

    fun getSubmittedFlag(context: Context){
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        _submitted.value = preferences.getBoolean("submitted", false)
    }

}
