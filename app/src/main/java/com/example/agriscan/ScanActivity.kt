package com.example.agriscan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.agriscan.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantName = intent.getStringExtra("PLANT_NAME")

        binding.tvPredictionTitle.text = "Prediksi $plantName"
    }
}
