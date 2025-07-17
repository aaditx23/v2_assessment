package com.aaditx23.v2_assessment.application

import android.content.Context
import androidx.room.Room
import com.aaditx23.v2_assessment.data.local.room.AppDatabase

object RoomServer {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun init(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "v2_assessment_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
    val db: AppDatabase
        get() = INSTANCE ?: throw IllegalStateException("RoomServer not initialized")
}

