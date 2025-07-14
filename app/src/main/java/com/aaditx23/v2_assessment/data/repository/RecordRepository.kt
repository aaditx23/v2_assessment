package com.aaditx23.v2_assessment.data.repository

import com.aaditx23.v2_assessment.data.remote.api.RecordService
import com.aaditx23.v2_assessment.data.remote.model.ApiResponse
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordService: RecordService
) {

    suspend fun getRecords(): ApiResponse {
        return recordService.getRecords()
    }
}