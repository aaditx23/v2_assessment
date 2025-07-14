package com.aaditx23.v2_assessment.model.record

import com.squareup.moshi.Json

// Represents an option for multipleChoice, dropdown, or checkbox

data class Option(
    @Json(name = "value") val value: String,
    @Json(name = "referTo") val referTo: ReferId? = null
)

