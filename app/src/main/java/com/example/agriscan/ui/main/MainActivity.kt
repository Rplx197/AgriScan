package com.example.agriscan.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agriscan.R
import com.example.agriscan.ui.setting.SettingActivity
import com.example.agriscan.ui.auth.SignInActivity
import com.example.agriscan.adapter.MenuAdapter
import com.example.agriscan.adapter.ScanAdapter
import com.example.agriscan.data.model.MenuItem
import com.example.agriscan.data.model.PlantItem
import com.example.agriscan.databinding.ActivityMainBinding
import com.example.agriscan.ui.example.ExampleActivity
import com.example.agriscan.ui.history.HistoryActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class MainActivity : AppCompatActivity(), MenuAdapter.OnMenuItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val sharedPreferences = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

        setGreetingMessage()

        val plantList = listOf(
            PlantItem("Tomat", R.drawable.bg_tomato, R.drawable.tomato),
            PlantItem("Jagung", R.drawable.bg_corn, R.drawable.corn),
            PlantItem("Kentang", R.drawable.bg_potato, R.drawable.potato)
        )

        val menuList = listOf(
            MenuItem("Contoh", R.drawable.ic_example),
            MenuItem("Riwayat", R.drawable.ic_history),
            MenuItem("Pengaturan", R.drawable.ic_setting),
            MenuItem("Keluar", R.drawable.ic_sign_out)
        )

        val adapterScan = ScanAdapter(this, plantList)
        binding.rvPlant.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPlant.adapter = adapterScan

        val adapterMenu = MenuAdapter(menuList, this)
        binding.rvMenu.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMenu.adapter = adapterMenu
    }

    override fun onMenuItemClick(menuItem: MenuItem) {
        when (menuItem.name) {
            "Contoh" -> startActivity(Intent(this, ExampleActivity::class.java))
            "Riwayat" -> startActivity(Intent(this, HistoryActivity::class.java))
            "Pengaturan" -> startActivity(Intent(this, SettingActivity::class.java))
            "Keluar" -> {
                showSignOutDialog()
            }
        }
    }

    private fun showSignOutDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Keluar")
            setMessage("Apa anda yakin ingin keluar?")
            setPositiveButton("Ya") { _, _ ->
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("remember_me", false).apply()

                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainActivity, SignInActivity::class.java))
                finish()
            }
            setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun setGreetingMessage() {
        val currentUser = auth.currentUser
        val displayEmail = currentUser?.email ?: "Tamu"

        val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 5..11 -> "Selamat Pagi"
            in 12..17 -> "Selamat Siang"
            in 18..20 -> "Selamat Sore"
            else -> "Selamat Malam"
        }

        binding.tvGreeting.text = getString(R.string.greeting_message, greeting, displayEmail)
    }
}