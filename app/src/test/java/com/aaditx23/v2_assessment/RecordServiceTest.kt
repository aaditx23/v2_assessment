package com.aaditx23.v2_assessment

import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.MockResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.aaditx23.v2_assessment.data.remote.api.RecordService
import com.aaditx23.v2_assessment.data.remote.model.ApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class RecordServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: RecordService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RecordService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetRecords() = runBlocking {

        val response: ApiResponse = service.getRecords()
        response.printRecord()

    }
}