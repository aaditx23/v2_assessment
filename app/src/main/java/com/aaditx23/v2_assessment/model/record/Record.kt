package com.aaditx23.v2_assessment.model.record

import com.squareup.moshi.Json

// Represents a single record/question block

data class Record(
    @Json(name = "id") val id: Int,
    @Json(name = "skip") val skip: ReferId,
    @Json(name = "type") val type: String,
    @Json(name = "options") val options: List<Option>?,
    @Json(name = "referTo") val referTo: ReferId?,
    @Json(name = "question") val question: Question?,
    @Json(name = "validations") val validations: Validations?
)

