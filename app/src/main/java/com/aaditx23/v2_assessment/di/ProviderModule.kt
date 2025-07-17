package com.aaditx23.v2_assessment.di


import com.aaditx23.v2_assessment.application.RetrofitServer
import com.aaditx23.v2_assessment.application.RoomServer
import com.aaditx23.v2_assessment.data.local.room.Answer.AnswerDao
import com.aaditx23.v2_assessment.data.local.room.AppDatabase
import com.aaditx23.v2_assessment.data.local.room.Submission.SubmissionDao
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

    @Provides
    @Singleton
    fun providesAppDatabase(): AppDatabase{
        return RoomServer.db
    }

    @Provides
    @Singleton
    fun provideAnswerDao(database: AppDatabase): AnswerDao {
        return database.answerDao()
    }

    @Provides
    @Singleton
    fun provideSubmissionDao(database: AppDatabase): SubmissionDao {
        return database.submissionDao()
    }
}