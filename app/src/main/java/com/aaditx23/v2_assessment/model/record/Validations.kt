package com.aaditx23.v2_assessment.model.record

import com.squareup.moshi.Json

// Represents validation rules for inputs

data class Validations(
    @Json(name = "regex") val regex: String
)

