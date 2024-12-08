package com.example.agriscan.ui.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agriscan.R
import com.example.agriscan.adapter.ExampleAdapter
import com.example.agriscan.data.model.DiseaseItem
import com.example.agriscan.data.model.ExampleItem
import com.example.agriscan.databinding.ActivityExampleBinding

class ExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantExamples = listOf(
            ExampleItem(
                "Tomat", listOf(
                    DiseaseItem(
                        "Bacterial Spot",
                        "Gunakan fungisida berbasis tembaga.",
                        R.drawable.tomato_bacterial_spot,
                        "fungisida tembaga"
                    ),
                    DiseaseItem(
                        "Early Blight",
                        "Gunakan mankozeb atau fungisida klorotalonil.",
                        R.drawable.tomato_early_blight,
                        "fungisida klorotalonil"
                    ),
                    DiseaseItem(
                        "Septoria Leaf",
                        "Gunakan fungisida berbasis tembaga atau klorotalonil.",
                        R.drawable.tomato_septoria_leaf,
                        "fungisida tembaga"
                    ),
                    DiseaseItem(
                        "Mosaic",
                        "Gunakan varietas tahan virus.",
                        R.drawable.tomato_mosaic,
                        "varietas tahan virus"
                    ),
                    DiseaseItem(
                        "Healthy",
                        "Tanaman sehat, tidak perlu obat.",
                        R.drawable.tomato_healthy,
                        ""
                    ),
                    DiseaseItem(
                        "Two-Spotted Spider Mites",
                        "Gunakan akarisida berbasis abamektin.",
                        R.drawable.tomato_two_spotted_spider_mites,
                        "akarisida abamektin"
                    ),
                    DiseaseItem(
                        "Yellow Leaf Curl Virus",
                        "Gunakan varietas tahan virus dan pengendalian vektor.",
                        R.drawable.tomato_yellow_leaf_curl_virus,
                        "benih tahan virus tomat"
                    ),
                    DiseaseItem(
                        "Late Blight",
                        "Gunakan fungisida berbasis tembaga atau fosfit.",
                        R.drawable.tomato_late_blight,
                        "fungisida tembaga"
                    ),
                    DiseaseItem(
                        "Leaf Mold",
                        "Gunakan fungisida berbasis mankozeb atau klorotalonil.",
                        R.drawable.tomato_leaf_mold,
                        "fungisida mankozeb"
                    ),
                    DiseaseItem(
                        "Target Spot",
                        "Gunakan fungisida berbasis klorotalonil atau strobulirin.",
                        R.drawable.tomato_target_spot,
                        "fungisida strobulirin"
                    )
                )
            ),
            ExampleItem(
                "Jagung", listOf(
                    DiseaseItem(
                        "Blight",
                        "Gunakan fungisida berbasis strobilurin.",
                        R.drawable.corn_blight,
                        "fungisida strobilurin"
                    ),
                    DiseaseItem(
                        "Common Rust",
                        "Gunakan fungisida triazol.",
                        R.drawable.corn_common_rust,
                        "fungisida triazol"
                    ),
                    DiseaseItem(
                        "Gray Leaf Spot",
                        "Gunakan fungisida berbasis klorotalonil.",
                        R.drawable.corn_gray_leaf_spot,
                        "fungisida klorotalonil"
                    ),
                    DiseaseItem(
                        "Healthy",
                        "Tanaman sehat, tidak perlu obat.",
                        R.drawable.corn_healthy,
                        ""
                    )
                )
            ),
            ExampleItem(
                "Kentang", listOf(
                    DiseaseItem(
                        "Early Blight",
                        "Gunakan fungisida berbasis mankozeb.",
                        R.drawable.potato_early_blight,
                        "fungisida mankozeb"
                    ),
                    DiseaseItem(
                        "Healthy",
                        "Tanaman sehat, tidak perlu obat.",
                        R.drawable.potato_healthy,
                        ""
                    ),
                    DiseaseItem(
                        "Late Blight",
                        "Gunakan fungisida berbasis tembaga atau fosfit.",
                        R.drawable.potato_late_blight,
                        "fungisida tembaga"
                    )
                )
            )
        )

        val adapter = ExampleAdapter(this, plantExamples)
        binding.rvPlantsExample.layoutManager = LinearLayoutManager(this)
        binding.rvPlantsExample.adapter = adapter
    }
}