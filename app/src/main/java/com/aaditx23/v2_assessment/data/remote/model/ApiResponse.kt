package com.aaditx23.v2_assessment.data.remote.model

import com.aaditx23.v2_assessment.model.Metadata
import com.aaditx23.v2_assessment.model.record.Record
import com.squareup.moshi.Json

// Represents the main API response

data class ApiResponse(
    @Json(name = "record") val record: List<Record>,
    @Json(name = "metadata") val metadata: Metadata?
)

