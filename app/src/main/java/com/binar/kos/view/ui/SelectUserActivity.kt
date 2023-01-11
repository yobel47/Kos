package com.binar.kos.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.kos.databinding.ActivitySelectUserBinding

class SelectUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this,LoginActivity::class.java)
        binding.btnPencariKos.setOnClickListener {
            intent.putExtra(USER_TYPE,"pencari")
            startActivity(intent)
        }
        binding.btnPenyewaKos.setOnClickListener {
            intent.putExtra(USER_TYPE,"penyewa")
            startActivity(intent)
        }
    }

    companion object{
        const val USER_TYPE = "user_type"
    }
}