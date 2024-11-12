package com.example.agriscan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.agriscan.data.model.MenuItem
import com.example.agriscan.R
import com.example.agriscan.databinding.ItemMenuBinding

class MenuAdapter(
    private val menuList: List<MenuItem>,
    private val onMenuItemClickListener: OnMenuItemClickListener
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    interface OnMenuItemClickListener {
        fun onMenuItemClick(menuItem: MenuItem)
    }

    inner class MenuViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: MenuItem) {
            binding.tvMenuName.text = menuItem.name
            binding.ivMenuIcon.setImageResource(menuItem.icon)
            binding.cvMenuItem.setBackgroundResource(R.drawable.bg_menu)

            binding.root.setOnClickListener {
                onMenuItemClickListener.onMenuItemClick(menuItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuList[position])

        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation)
        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onViewAttachedToWindow(holder: MenuViewHolder) {
        super.onViewAttachedToWindow(holder)
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation)
        holder.itemView.startAnimation(animation)
    }
}
