package com.aaditx23.v2_assessment.model.record

import com.squareup.moshi.Json

// Represents a reference to another question by ID

data class ReferId(
    @Json(name = "id") val id: String
)

