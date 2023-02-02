package com.binar.kos.view.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.auth0.android.jwt.JWT
import com.binar.kos.databinding.ActivityLogoutBinding
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LogoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoutBinding
    private val dataStore: DatastoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onLogout()
        getUsername()

    }

    private fun getUsername() {
        dataStore.getAccessToken().observe(this) { token ->
            if(!token.equals("default value")){
                val jwt = JWT(token.toString())
                Log.e("jwt", token.toString())
                val username = jwt.getClaim("user_name").asString()
                binding.tvUsernameProfile.text = username.toString()
                binding.tvNoProfile.isVisible = false
            }
        }
    }

    private fun onLogout() {
        binding.logoutButton.setOnClickListener {
            dataStore.deleteAllData()
            val intent = Intent(this, HomeActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }
}

