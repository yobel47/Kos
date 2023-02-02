package com.binar.kos.view.ui.logout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.auth0.android.jwt.JWT
import com.binar.kos.databinding.ActivityLogoutBinding
import com.binar.kos.utils.Status
import com.binar.kos.utils.hideLoading
import com.binar.kos.utils.showLoading
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.LoginViewModel
import com.binar.kos.viewmodel.LogoutViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LogoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoutBinding
    private val logoutViewModel: LogoutViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvUsernameProfile.isVisible = false
        binding.tvNoProfile.isVisible = false

        onLogout()
        getUsername()
    }

    private fun getUsername() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                logoutViewModel.getUsedata(token).observe(this@LogoutActivity) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                        }
                        Status.SUCCESS -> {
                            binding.tvUsernameProfile.isVisible = true
                            binding.tvUsernameProfile.text = result.data!!.data!!.namaLengkap
                            if (result.data.data!!.noTelepon !== null){
                                binding.tvNoProfile.isVisible = true
                                binding.tvNoProfile.text = result.data.data!!.noTelepon.toString()
                            }
                        }
                        Status.ERROR -> {
                        }
                    }
                }
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

