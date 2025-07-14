package com.aaditx23.v2_assessment.ui.screens.main

import com.aaditx23.v2_assessment.data.remote.model.ApiResponse
import com.aaditx23.v2_assessment.model.record.Record


sealed class MainScreenState {
    object Loading : MainScreenState()
    data class Success(val records: List<Record>) : MainScreenState()
    data class Error(val error: String) : MainScreenState()
}
