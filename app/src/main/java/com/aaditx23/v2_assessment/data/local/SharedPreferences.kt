package com.aaditx23.v2_assessment.data.local

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SharedPreferences{
    private val _submitted = MutableStateFlow(false)
    val submitted: StateFlow<Boolean> = _submitted

    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled

    fun setSubmitted(context: Context, value: Boolean) {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        preferences.edit { putBoolean("submitted", value) }
        _submitted.value = value
    }

    fun isSubmitted(context: Context){
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val value = preferences.getBoolean("submitted", false)
        _submitted.value = value
    }

    fun setDarkModeEnabled(context: Context, enabled: Boolean) {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        preferences.edit { putBoolean("darkMode", enabled) }
        _darkModeEnabled.value = enabled
    }

    fun isDarkModeEnabled(context: Context){
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val value = preferences.getBoolean("darkMode", false)
        _darkModeEnabled.value = value
    }
}