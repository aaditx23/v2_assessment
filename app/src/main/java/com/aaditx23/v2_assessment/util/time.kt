package com.aaditx23.v2_assessment.util

import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun getTime(millis: Long): String? {
    return java.time.Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(("yyyy-MM-dd|HH:mm:ss a")))
}