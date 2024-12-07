package com.example.agriscan.ui.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agriscan.R
import com.example.agriscan.adapter.PlantExampleAdapter
import com.example.agriscan.data.model.DiseaseItem
import com.example.agriscan.data.model.PlantExampleItem
import com.example.agriscan.databinding.ActivityExampleBinding

class ExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantExamples = listOf(
            PlantExampleItem(
                "Tomat", listOf(
                    DiseaseItem("Bacterial Spot", "Gunakan fungisida berbasis tembaga.", R.drawable.tomato_bacterial_spot),
                    DiseaseItem("Early Blight", "Gunakan mankozeb atau fungisida klorotalonil.", R.drawable.tomato_early_blight),
                    DiseaseItem("Septoria Leaf", "Gunakan fungisida berbasis tembaga atau klorotalonil.", R.drawable.tomato_septoria_leaf),
                    DiseaseItem("Mosaic", "Gunakan varietas tahan virus.", R.drawable.tomato_mosaic),
                    DiseaseItem("Healthy", "Tanaman sehat, tidak perlu obat.", R.drawable.tomato_healthy)
                )
            ),
            PlantExampleItem(
                "Jagung", listOf(
                    DiseaseItem("Blight", "Gunakan fungisida berbasis strobilurin.", R.drawable.corn_blight),
                    DiseaseItem("Common Rust", "Gunakan fungisida triazol.", R.drawable.corn_common_rust),
                    DiseaseItem("Gray Leaf Spot", "Gunakan fungisida berbasis klorotalonil.", R.drawable.corn_gray_leaf_spot),
                    DiseaseItem("Healthy", "Tanaman sehat, tidak perlu obat.", R.drawable.corn_healthy)
                )
            ),
            PlantExampleItem(
                "Kentang", listOf(
                    DiseaseItem("Early Blight", "Gunakan fungisida berbasis mankozeb.", R.drawable.potato_early_blight),
                    DiseaseItem("Healthy", "Tanaman sehat, tidak perlu obat.", R.drawable.potato_healthy),
                    DiseaseItem("Late Blight", "Gunakan fungisida berbasis tembaga atau fosfit.", R.drawable.potato_late_blight)
                )
            )
        )

        val adapter = PlantExampleAdapter(this, plantExamples)
        binding.rvPlantsExample.layoutManager = LinearLayoutManager(this)
        binding.rvPlantsExample.adapter = adapter
    }
}