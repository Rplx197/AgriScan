package com.example.agriscan.ui.scan

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.agriscan.R
import com.example.agriscan.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private var imageUri: Uri? = null

    // Register an activity result launcher to pick an image from the gallery
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                binding.ivPrediction.setImageURI(imageUri)  // Display the selected image
                val bitmap = loadBitmapFromUri(it)

                // Pass the bitmap to your ML model here
                if (bitmap != null) {
                    // Call your ML model's prediction method with `bitmap`
                    // e.g., val result = model.predict(bitmap)
                } else {
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        val plantName = intent.getStringExtra("PLANT_NAME")
        binding.tvPredictionTitle.text = getString(R.string.prediksi, plantName)

        binding.btnPredict.setOnClickListener {
            openFileChooser()
        }

        binding.btnSaveHistory.setOnClickListener {
            saveToHistory()
        }
    }

    // Open file chooser using the registered activity result launcher
    private fun openFileChooser() {
        pickImageLauncher.launch("image/*")
    }

    // Function to load bitmap from URI, handling API differences
    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // For API 28 and above, use ImageDecoder
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
            } else {
                // For older APIs, use the deprecated method
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    .copy(Bitmap.Config.ARGB_8888, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun saveToHistory() {
        Toast.makeText(this, "History saved!", Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvPredictionTitle, View.ALPHA, 1f).setDuration(200)
        val image = ObjectAnimator.ofFloat(binding.ivPrediction, View.ALPHA, 1f).setDuration(200)
        val result = ObjectAnimator.ofFloat(binding.tvPredictionResult, View.ALPHA, 1f).setDuration(200)
        val confidence = ObjectAnimator.ofFloat(binding.tvPredictionConfidence, View.ALPHA, 1f).setDuration(200)
        val button = ObjectAnimator.ofFloat(binding.btnPredict, View.ALPHA, 1f).setDuration(200)
        val save = ObjectAnimator.ofFloat(binding.btnSaveHistory, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(title, image, result, confidence, button, save)
            start()
        }
    }
}
