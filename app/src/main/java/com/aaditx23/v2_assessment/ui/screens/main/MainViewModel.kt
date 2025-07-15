package com.aaditx23.v2_assessment.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.aaditx23.v2_assessment.data.repository.RecordRepository
import com.aaditx23.v2_assessment.model.answer.Answer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.aaditx23.v2_assessment.ui.screens.main.JsonFlow.AnswerState


@HiltViewModel
class MainViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state: StateFlow<MainScreenState> = _state

    private val _answerState = MutableStateFlow(AnswerState())
    val answerState: StateFlow<AnswerState> = _answerState

    fun updateAnswer(questionId: String, answer: Answer) {
        val current = _answerState.value
        current.addAnswer(questionId, answer)
        _answerState.value = current
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
}
