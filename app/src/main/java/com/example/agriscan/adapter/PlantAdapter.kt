package com.example.agriscan.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.agriscan.R
import com.example.agriscan.data.model.PlantItem
import com.example.agriscan.ui.scan.ScanActivity
import com.example.agriscan.databinding.ItemPlantBinding

class PlantAdapter(private val context: Context, private val plantList: List<PlantItem>) :
    RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(private val binding: ItemPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plantItem: PlantItem) {
            binding.tvPlant.text = plantItem.name
            binding.ivPlant.setImageResource(plantItem.icon)
            binding.cvPlant.setBackgroundResource(plantItem.background)

            binding.root.setOnClickListener {
                val intent = Intent(context, ScanActivity::class.java)
                intent.putExtra("PLANT_NAME", plantItem.name)
                context.startActivity(intent)

                if (context is Activity) {
                    context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(plantList[position])

        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation)
        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onViewAttachedToWindow(holder: PlantViewHolder) {
        super.onViewAttachedToWindow(holder)
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation)
        holder.itemView.startAnimation(animation)
    }
}
