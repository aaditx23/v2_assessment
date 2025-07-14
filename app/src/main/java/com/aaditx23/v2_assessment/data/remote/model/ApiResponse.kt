package com.aaditx23.v2_assessment.data.remote.model

import com.aaditx23.v2_assessment.model.Metadata
import com.aaditx23.v2_assessment.model.record.Record
import com.squareup.moshi.Json


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

    fun toRecordString(): String {
        val builder = StringBuilder()
        record.forEach { rec ->
            builder.append("Record ID: ${rec.id}\n")
            builder.append("Type: ${rec.type}\n")
            builder.append("Question: ${rec.question?.slug}\n")
            builder.append("Skip ID: ${rec.skip.id}\n")
            builder.append("ReferTo ID: ${rec.referTo?.id}\n")
            builder.append("Options:\n")
            rec.options?.forEach { option ->
                builder.append("  Value: ${option.value}\n")
                builder.append("  ReferTo: ${option.referTo?.id}\n")
            }
            if (rec.validations != null) {
                builder.append("Validations: regex = ${rec.validations.regex}\n")
            }
            builder.append("---------------------------\n")
        }
        builder.append("Metadata: ${metadata}\n")
        return builder.toString()
    }
}
