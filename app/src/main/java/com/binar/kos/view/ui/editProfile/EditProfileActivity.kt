package com.binar.kos.view.ui.editProfile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.binar.kos.databinding.ActivityEditProfileBinding

class EditProfileActivity: AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}