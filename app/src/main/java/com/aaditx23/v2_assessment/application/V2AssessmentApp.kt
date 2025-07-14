package com.aaditx23.v2_assessment.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class V2AssessmentApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitServer.initializeRetrofit()
    }
}

