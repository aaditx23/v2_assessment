package com.aaditx23.v2_assessment.ui.screens.main

import com.aaditx23.v2_assessment.data.remote.model.ApiResponse


sealed class MainScreenState {
    object Loading : MainScreenState()
    data class Success(val records: ApiResponse) : MainScreenState()
    data class Error(val error: String) : MainScreenState()
}
