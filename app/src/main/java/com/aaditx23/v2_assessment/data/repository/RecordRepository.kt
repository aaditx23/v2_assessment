package com.aaditx23.v2_assessment.data.repository

import com.aaditx23.v2_assessment.data.remote.model.ApiResponse
import com.aaditx23.v2_assessment.data.remote.service.RecordService
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordService: RecordService
) {

    suspend fun getRecords(): ApiResponse {
        return recordService.getRecords()
    }
}