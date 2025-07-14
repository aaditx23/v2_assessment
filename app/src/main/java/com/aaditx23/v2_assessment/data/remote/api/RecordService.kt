package com.aaditx23.v2_assessment.data.remote.api

import com.aaditx23.v2_assessment.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Url
import com.aaditx23.v2_assessment.data.remote.model.ApiResponse

interface RecordService {
    @GET
    suspend fun getRecords(@Url url: String = "${BuildConfig.BASE_URL}/v3/b/687374506063391d31aca23a"): ApiResponse

}