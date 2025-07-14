package com.aaditx23.v2_assessment.model.record

import com.squareup.moshi.Json

// Represents the question text

data class Question(
    @Json(name = "slug") val slug: String
)

