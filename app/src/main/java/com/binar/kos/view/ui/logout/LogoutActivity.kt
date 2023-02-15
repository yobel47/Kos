package com.binar.kos.view.ui.logout

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.binar.kos.R
import com.binar.kos.databinding.ActivityLogoutBinding
import com.binar.kos.databinding.CardLogoutBinding
import com.binar.kos.utils.Status
import com.binar.kos.utils.hideLoading
import com.binar.kos.utils.showLoading
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.profile.ProfileActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.LogoutViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class LogoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoutBinding
    private lateinit var isdialog: AlertDialog
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
        onEditUser()
    }

    private fun getUsername() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                logoutViewModel.getUsedata(token).observe(this@LogoutActivity) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                            showLoading(this)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            binding.tvUsernameProfile.isVisible = true
                            binding.tvUsernameProfile.text = result.data!!.data!!.namaLengkap
                            if (result.data.data!!.noTelepon !== null){
                                binding.tvNoProfile.isVisible = true
                                binding.tvNoProfile.text = result.data.data!!.noTelepon.toString()
                            }
                            if(!result.data.data!!.image.isNullOrEmpty()){
                                val requestOptions = RequestOptions().placeholder(R.drawable.profile_empty)
                                Glide.with(this).load(result.data.data.image).apply(requestOptions).skipMemoryCache(true)
                                    .into(binding.ivProfileImage)
                            }

                        }
                        Status.ERROR -> {
                            showLoading(this)
                            Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun onLogout() {
        binding.logoutButton.setOnClickListener {
            val logoutBinding: CardLogoutBinding =
                CardLogoutBinding.inflate(LayoutInflater.from(this))
            val builder = AlertDialog.Builder(this)
            builder.setView(logoutBinding.root)
            builder.setCancelable(true)
            logoutBinding.btnCancel.setOnClickListener {
                isdialog.dismiss()
            }
            logoutBinding.btnLogout.setOnClickListener {
                dataStore.deleteAllData()
                val intent = Intent(this, HomeActivity::class.java)
                finishAffinity()
                startActivity(intent)
            }
            isdialog = builder.create()
            isdialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isdialog.show()
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            val window: Window = isdialog.window!!
            val wlp: WindowManager.LayoutParams = window.attributes
            wlp.gravity = Gravity.BOTTOM
            wlp.width = width
            window.attributes = wlp
        }
    }

    private fun onEditUser() {
        binding.profileSection.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}

