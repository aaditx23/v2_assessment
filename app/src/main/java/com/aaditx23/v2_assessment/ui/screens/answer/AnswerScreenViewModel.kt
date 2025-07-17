package com.aaditx23.v2_assessment.ui.screens.answer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaditx23.v2_assessment.data.repository.AnswerRepository
import com.aaditx23.v2_assessment.data.repository.RecordRepository
import com.aaditx23.v2_assessment.model.record.Record
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerScreenViewModel @Inject constructor(
    private val answerRepository: AnswerRepository,
    private val recordRepository: RecordRepository
): ViewModel() {

    private val _answerScreenState = MutableStateFlow<AnswerScreenState>(AnswerScreenState.Loading)
    val answerScreenState: StateFlow<AnswerScreenState> = _answerScreenState

    private val _records = MutableStateFlow<List<Record>>(listOf())
    val records: StateFlow<List<Record>> = _records

    fun refresh(id: Long){
        fetchRecords()
        getAnswers(id)
    }

    fun fetchRecords(){
        viewModelScope.launch {
            try{
                val response = recordRepository.getRecords()
                _records.value = response.record
            }
            catch (e: Exception){
                _answerScreenState.value = AnswerScreenState.Error("Records could not be fetched")

            }
        }
    }

    fun getAnswers(id: Long){
        viewModelScope.launch {
            val answers = answerRepository.getAnswersBySubmissionId(id)
            _answerScreenState.value = AnswerScreenState.ShowAnswers(answers)
        }
    }

}