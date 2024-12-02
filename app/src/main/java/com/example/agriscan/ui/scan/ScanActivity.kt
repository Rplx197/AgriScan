package com.example.agriscan.ui.scan

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.agriscan.R
import com.example.agriscan.Utils
import com.example.agriscan.Utils.reduceFileImage
import com.example.agriscan.Utils.uriToFile
import com.example.agriscan.databinding.ActivityScanBinding
import com.example.agriscan.network.ApiConfig
import com.example.agriscan.network.ImageUploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantName = intent.getStringExtra("PLANT_NAME")
        binding.tvPredictionTitle.text = getString(R.string.prediksi, plantName)

        setupListeners()
        playAnimation()
    }

    private fun setupListeners() {
        binding.btnGallery.setOnClickListener { chooseImageFromGallery() }
        binding.btnSaveHistory.setOnClickListener { saveToHistory() }
        binding.btnCamera.setOnClickListener { chooseImageFromCamera() }
    }

    private fun chooseImageFromGallery() {
        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun chooseImageFromCamera() {
        currentImageUri = Utils.getImageUri(this)
        currentImageUri?.let { cameraLauncher.launch(it) }
            ?: Toast.makeText(this, "Failed to initialize camera", Toast.LENGTH_SHORT).show()
    }

    private fun saveToHistory() {
        Toast.makeText(this, "History saved!", Toast.LENGTH_SHORT).show()
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            currentImageUri = uri
            showSelectedImage()
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            showSelectedImage()
        } else {
            currentImageUri = null
            Toast.makeText(this, "Camera capture failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSelectedImage() {
        currentImageUri?.let {
            binding.ivPrediction.setImageURI(it)
            val imageFile = uriToFile(it, this)
            uploadImageToServer(imageFile)
        } ?: Log.d("ScanActivity", "No image to show")
    }

    private fun uploadImageToServer(imageFile: File) {
        val reducedFile = imageFile.reduceFileImage()
        val requestFile = reducedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", reducedFile.name, requestFile)

        showLoading(true)

        ApiConfig.getApiService().uploadImage(body).enqueue(object : Callback<ImageUploadResponse> {
            override fun onResponse(call: Call<ImageUploadResponse>, response: Response<ImageUploadResponse>) {
                showLoading(false)

                if (response.isSuccessful) {
                    val result = response.body()?.top_predictions?.firstOrNull()
                    if (result != null) {
                        binding.tvPredictionResult.text = result.label
                        binding.tvPredictionConfidence.text = String.format("%.2f%%", result.confidence * 100)
                        Toast.makeText(this@ScanActivity, "Upload Successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.tvPredictionResult.text = "No result"
                        binding.tvPredictionConfidence.text = ""
                        Toast.makeText(this@ScanActivity, "No predictions found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("Upload Error", response.errorBody()?.string() ?: "Unknown error")
                    Toast.makeText(this@ScanActivity, "Upload failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                showLoading(false)

                Log.e("Upload Failure", t.message ?: "Unknown error")
                Toast.makeText(this@ScanActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnGallery.isEnabled = !isLoading
        binding.btnSaveHistory.isEnabled = !isLoading
        binding.btnCamera.isEnabled = !isLoading
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvPredictionTitle, View.ALPHA, 1f).setDuration(200)
        val image = ObjectAnimator.ofFloat(binding.ivPrediction, View.ALPHA, 1f).setDuration(200)
        val result = ObjectAnimator.ofFloat(binding.tvPredictionResult, View.ALPHA, 1f).setDuration(200)
        val confidence = ObjectAnimator.ofFloat(binding.tvPredictionConfidence, View.ALPHA, 1f).setDuration(200)
        val gallery = ObjectAnimator.ofFloat(binding.btnGallery, View.ALPHA, 1f).setDuration(200)
        val camera = ObjectAnimator.ofFloat(binding.btnCamera, View.ALPHA, 1f).setDuration(200)
        val save = ObjectAnimator.ofFloat(binding.btnSaveHistory, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(title, image, result, confidence, camera, gallery, save)
            start()
        }
    }
}
