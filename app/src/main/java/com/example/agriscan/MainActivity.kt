package com.example.agriscan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agriscan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantList = listOf(
            PlantItem("Singkong", R.drawable.bg_cassava, R.drawable.potato),
            PlantItem("Tomat", R.drawable.bg_tomato, R.drawable.tomato),
            PlantItem("Kopi", R.drawable.bg_coffee, R.drawable.coffee),
            PlantItem("Kentang", R.drawable.bg_potato, R.drawable.potato),
        )

        val menuList = listOf(
            MenuItem("History", R.drawable.ic_history),
            MenuItem("History", R.drawable.ic_history),
            MenuItem("History", R.drawable.ic_history)
        )

        val adapterPlant = PlantAdapter(this, plantList)
        binding.rvPlant.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPlant.adapter = adapterPlant

        val adapterMenu = MenuAdapter(menuList)
        binding.rvMenu.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMenu.adapter = adapterMenu
    }
}