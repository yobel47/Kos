package com.binar.kos.view.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.binar.kos.databinding.ActivitySplashScreenBinding
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.homePenyewa.HomePenyewaActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val dataStore: DatastoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        @Suppress("DEPRECATION")
        Handler().postDelayed({
            checkLogin()
        }, 3000)
    }

    private fun checkLogin() {
        dataStore.getLoginState().observe(this) { it ->
            if (it) {
                dataStore.getRole().observe(this) { role ->
                    if (role == "ROLE_PENYEWA") {
                        val intent = Intent(this, HomePenyewaActivity::class.java)
                        finishAffinity()
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, HomeActivity::class.java)
                        finishAffinity()
                        startActivity(intent)
                    }
                }
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                finishAffinity()
                startActivity(intent)
            }
        }
    }

}