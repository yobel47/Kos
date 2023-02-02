package com.binar.kos.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.kos.R
import com.binar.kos.databinding.ActivityMainBinding
import com.binar.kos.databinding.ActivityVerificationBinding
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.homePenyewa.HomePenyewaActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataStore: DatastoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}