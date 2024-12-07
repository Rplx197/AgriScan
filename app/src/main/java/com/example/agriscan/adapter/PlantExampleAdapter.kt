package com.example.agriscan.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agriscan.R
import com.example.agriscan.data.model.PlantExampleItem
import com.example.agriscan.databinding.ItemPlantExampleBinding

class PlantExampleAdapter(
    private val context: Context,
    private val plantExamples: List<PlantExampleItem>
) : RecyclerView.Adapter<PlantExampleAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPlantExampleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlantExampleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plantExamples[position]
        holder.binding.tvPlantName.text = plant.name
        holder.binding.vpDiseases.adapter = DiseaseAdapter(context, plant.diseases)
    }

    override fun getItemCount(): Int = plantExamples.size
}