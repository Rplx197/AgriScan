package com.example.agriscan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.agriscan.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private val viewModel: SettingsViewModel by viewModels { SettingsViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe the theme LiveData
        viewModel.isDarkModeLiveData.observe(this) { isDarkMode ->
            binding.switchTheme.isChecked = isDarkMode
            applyTheme(isDarkMode)
        }

        // Toggle theme when switch is clicked
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleTheme(isChecked)
        }
    }

    private fun applyTheme(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
