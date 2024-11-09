package com.example.agriscan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    val isDarkModeLiveData: LiveData<Boolean> = repository.isDarkModeLiveData

    fun toggleTheme(isDarkMode: Boolean) {
        repository.setDarkMode(isDarkMode)
    }
}
