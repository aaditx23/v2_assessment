package com.aaditx23.v2_assessment.data.remote.model

import com.aaditx23.v2_assessment.model.Metadata
import com.aaditx23.v2_assessment.model.record.Record
import com.squareup.moshi.Json

// Represents the main API response

data class ApiResponse(
    @Json(name = "record") val record: List<Record>,
    @Json(name = "metadata") val metadata: Metadata?
) {
    fun printRecord() {
        record.forEach { rec ->
            println("Record ID: ${rec.id}")
            println("Type: ${rec.type}")
            println("Question: ${rec.question?.slug}")
            println("Skip ID: ${rec.skip.id}")
            println("ReferTo ID: ${rec.referTo?.id}")
            println("Options:")
            rec.options?.forEach { option ->
                println("  Value: ${option.value}")
                println("  ReferTo: ${option.referTo?.id}")
            }
            if (rec.validations != null) {
                println("Validations: regex = ${rec.validations.regex}")
            }
            println("---------------------------")
        }
        println("Metadata: ${metadata}")
    }
}
