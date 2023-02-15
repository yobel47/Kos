package com.binar.kos.view.ui.favorit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.kos.databinding.ActivityFavoritBinding
import com.binar.kos.view.ui.history.HistoryActivity
import com.binar.kos.view.ui.logout.LogoutActivity

class FavoritActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toHome()
        toProfile()
        toHistory()
    }

    private fun toHome() {
        binding.btnHome.setOnClickListener {
            finish()
        }
    }

    private fun toProfile(){
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, LogoutActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun toHistory(){
        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

}