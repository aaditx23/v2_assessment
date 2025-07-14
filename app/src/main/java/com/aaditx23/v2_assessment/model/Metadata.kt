package com.aaditx23.v2_assessment.model

import com.squareup.moshi.Json

data class Metadata(
    @Json(name = "id") val id: String?,
    @Json(name = "private") val private: Boolean?,
    @Json(name = "createdAt") val createdAt: String?,
    @Json(name = "name") val name: String?
)