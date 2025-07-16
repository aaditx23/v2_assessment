package com.aaditx23.v2_assessment.application


import com.aaditx23.v2_assessment.BuildConfig
import com.aaditx23.v2_assessment.data.remote.service.RecordService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitServer {
    lateinit var retrofit: Retrofit
    private lateinit var moshi: Moshi
    val Record: RecordService by lazy {
        retrofit.create(RecordService::class.java)
    }

    fun init() {
        val client = OkHttpClient.Builder()
            .build()

        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .build()


    }


}
