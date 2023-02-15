@file:Suppress("DEPRECATION")

package com.binar.kos.view.ui.notification

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.kos.R
import com.binar.kos.databinding.ActivityNotificationBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.adapter.NotificationAdapter
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private val mainViewModel: MainViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()
    private lateinit var notificationAdapter: NotificationAdapter

    var accessToken = ""
    var notifType = 1

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
                getNotif("transaction")
            }
        }


        binding.tvBtnTransaksi.setOnClickListener {
            onClickNotifType(notifType)
        }

        binding.tvBtnInfo.setOnClickListener {
            onClickNotifType(notifType)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onClickNotifType(notif: Int) {
        if (notif == 0) {
            binding.tvBtnInfo.setBackgroundColor(resources.getColor(R.color.primary_container))
            binding.tvBtnInfo.setTextColor(resources.getColor(R.color.outline))
            binding.tvBtnTransaksi.setBackgroundColor(resources.getColor(R.color.primary))
            binding.tvBtnTransaksi.setTextColor(resources.getColor(R.color.white))
            notifType = 1
            binding.ivEmptyNotif.setImageResource(R.drawable.notif_2)
            binding.tvEmptyNotif.text = "Kamu belum punya transaksi nih.\n" +
                    "Yuk booking kost"
            getNotif("transaction")
        } else {
            binding.tvBtnTransaksi.setBackgroundColor(resources.getColor(R.color.primary_container))
            binding.tvBtnTransaksi.setTextColor(resources.getColor(R.color.outline))
            binding.tvBtnInfo.setBackgroundColor(resources.getColor(R.color.primary))
            binding.tvBtnInfo.setTextColor(resources.getColor(R.color.white))
            notifType = 0
            binding.ivEmptyNotif.setImageResource(R.drawable.notif_1)
            binding.tvEmptyNotif.text =
                "Jangan khawatir, kita pasti kasih tau kalau\nada informasi dan pemberitahuan untuk\nkamu."
            getNotif("info")
        }
    }


    private fun getNotif(category: String) {
        mainViewModel.getNotification(
            accessToken,
            category
        ).observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.rvNotif.isVisible = false
                    binding.cvEmptyRv.isVisible = false
                    binding.pbNotif.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.pbNotif.isVisible = false
                    val response = result.data
                    if (response!!.isEmpty()) {
                        binding.cvEmptyRv.isVisible = true
                    } else {
                        binding.rvNotif.isVisible = true
                        notificationAdapter = NotificationAdapter(response)
                        val linearLayoutManager = LinearLayoutManager(this)
                        binding.rvNotif.layoutManager = linearLayoutManager
                        binding.rvNotif.adapter = notificationAdapter
                    }
                }
                Status.ERROR -> {
                    binding.rvNotif.isVisible = false
                    binding.cvEmptyRv.isVisible = true
                    binding.pbNotif.isVisible = false
                    Toast.makeText(this,
                        "${result.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}