package com.binar.kos.view.ui.homePenyewa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.binar.kos.R
import com.binar.kos.databinding.ActivityHomePenyewaBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.ui.add.AddRoomActivity
import com.binar.kos.view.ui.editProfile.EditProfileActivity
import com.binar.kos.view.ui.historyPenyewa.HistoryPenyewaActivity
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.view.ui.logout.LogoutActivity
import com.binar.kos.view.ui.notification.NotificationActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.LogoutViewModel
import com.google.android.material.snackbar.Snackbar
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomePenyewaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePenyewaBinding
    private val dataStore: DatastoreViewModel by viewModel()
    private val logoutViewModel: LogoutViewModel by viewModel()

    private var userData = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePenyewaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserdata()
        setCarousel()
        toProfile()
        toAddRoom()
        toHistory()
        toNotif()

    }

    private fun setCarousel() {
        binding.ivCarousel.registerLifecycle(lifecycle)
        binding.ivCarousel.showIndicator = true
        binding.ivCarousel.showNavigationButtons = false
        binding.ivCarousel.showTopShadow = false
        binding.ivCarousel.showBottomShadow = false
        binding.ivCarousel.autoPlay = true
        binding.ivCarousel.autoPlayDelay = 3000

        val list = mutableListOf<CarouselItem>()
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.banner_1
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.banner_2
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.banner_3
            )
        )

        binding.ivCarousel.setData(list)
    }
    private fun toProfile() {
        dataStore.getLoginState().observe(this) {
            if (it) {
                binding.btnProfile.setOnClickListener {
                    val intent = Intent(this, LogoutActivity::class.java)
                    startActivity(intent)
                }
            } else {
                binding.btnProfile.setOnClickListener {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun getUserdata() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                logoutViewModel.getUsedata(token).observe(this) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                        }
                        Status.SUCCESS -> {
                            if (result.data!!.data!!.noTelepon == null) {
                                userData = false
                            }
                            if (result.data.data!!.bank == null) {
                                userData = false
                            }
                            if (result.data.data.gender == null) {
                                userData = false
                            }
                            if (result.data.data.kotaAsal == null) {
                                userData = false
                            }
                            if (result.data.data.noRekening == null) {
                                userData = false
                            }
                            if (result.data.data.profesi == null) {
                                userData = false
                            }
//                            if (result.data.data.tanggalKelahiran == null) {
//                                userData = false
//                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }


    private fun toAddRoom(){
        if(userData){
            binding.btnMyRoom.setOnClickListener {
                val intent = Intent(this, AddRoomActivity::class.java)
                startActivity(intent)
            }
        }else{
            Snackbar.make(binding.root,
                "Data profil belum terisi",
                Snackbar.LENGTH_LONG)
                .setAction("Isi data profile") {
                    val intent = Intent(this, EditProfileActivity::class.java)
                    finish()
                    startActivity(intent)
                }.show()
        }

    }

    private fun toHistory(){
        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryPenyewaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun toNotif(){
        binding.btnNotif.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }
}