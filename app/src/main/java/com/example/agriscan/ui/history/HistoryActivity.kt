package com.example.agriscan.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agriscan.adapter.HistoryAdapter
import com.example.agriscan.databinding.ActivityHistoryBinding
import com.example.agriscan.room.AppDatabase
import com.example.agriscan.room.History
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        setupRecyclerView()
        updateIconTintBasedOnTheme()
        loadHistoryData()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
    }

    private fun loadHistoryData() {
        lifecycleScope.launch {
            val historyList = db.historyDao().getAll()

            if (historyList.isEmpty()) {
                binding.layoutEmptyHistory.visibility = View.VISIBLE
                binding.rvHistory.visibility = View.GONE
            } else {
                binding.layoutEmptyHistory.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE

                adapter = HistoryAdapter(historyList) { history ->
                    deleteHistory(history)
                }
                binding.rvHistory.adapter = adapter
            }
        }
    }

    private fun deleteHistory(history: History) {
        lifecycleScope.launch {
            db.historyDao().delete(history)
            Toast.makeText(this@HistoryActivity, "History deleted!", Toast.LENGTH_SHORT).show()
            loadHistoryData()
        }
    }

    private fun updateIconTintBasedOnTheme() {
        val nightModeFlags = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        val tintColor = when (nightModeFlags) {
            android.content.res.Configuration.UI_MODE_NIGHT_YES -> ContextCompat.getColor(this, android.R.color.white)
            else -> ContextCompat.getColor(this, android.R.color.black)
        }
        binding.ivEmptyIcon.setColorFilter(tintColor)
    }

}