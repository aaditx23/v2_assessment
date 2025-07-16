package com.aaditx23.v2_assessment.ui.components.JsonFlow.states

data class RecordUiState(
    val currentId: String = "1",
    val nextId: String = "1",
    val showSubmit: Boolean = false,
    val hasError: Boolean = false,
    val hasValue: Boolean = false,
    val currentRecord: Record? = null,
)
