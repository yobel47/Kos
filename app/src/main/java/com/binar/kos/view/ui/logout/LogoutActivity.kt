package com.binar.kos.view.ui.logout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.binar.kos.databinding.ActivityLogout2Binding

class LogoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogout2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityLogout2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}