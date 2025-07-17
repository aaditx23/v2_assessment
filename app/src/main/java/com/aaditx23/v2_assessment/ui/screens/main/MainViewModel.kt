package com.aaditx23.v2_assessment.ui.screens.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaditx23.v2_assessment.data.repository.AnswerRepository
import com.aaditx23.v2_assessment.data.repository.RecordRepository
import com.aaditx23.v2_assessment.data.repository.SubmissionRepository
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.ReferId
import com.aaditx23.v2_assessment.ui.components.JsonFlow.states.AnswerState
import com.aaditx23.v2_assessment.ui.components.JsonFlow.states.RecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val submissionRepository: SubmissionRepository,
    private val answerRepository: AnswerRepository
) : ViewModel() {
    private val _mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val mainScreenState: StateFlow<MainScreenState> = _mainScreenState

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
            _mainScreenState.value = MainScreenState.Loading
            try {
                val response = recordRepository.getRecords().record
                _mainScreenState.value = MainScreenState.Success(response)
            } catch (e: Exception) {
                _mainScreenState.value = MainScreenState.Error(e.message ?: "Unknown error")
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
        }

    }


}
