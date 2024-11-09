package com.example.agriscan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agriscan.data.MenuItem
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
    }

    override fun getItemCount(): Int = menuList.size
}
