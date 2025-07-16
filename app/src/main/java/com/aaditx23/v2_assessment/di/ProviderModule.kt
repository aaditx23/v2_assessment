package com.aaditx23.v2_assessment.di


import com.aaditx23.v2_assessment.application.RetrofitServer
import com.aaditx23.v2_assessment.data.remote.service.RecordService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {

    @Provides
    @Singleton
    fun provideRecordService(): RecordService {
        return RetrofitServer.Record
    }
}