package com.binar.kos.view.ui.homePenyewa

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.kos.R
import com.binar.kos.databinding.ActivityHomeBinding
import com.binar.kos.databinding.ActivityHomePenyewaBinding
import com.binar.kos.view.ui.add.AddRoomActivity
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.view.ui.logout.LogoutActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.HomeViewModel
import com.google.android.material.card.MaterialCardView
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomePenyewaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePenyewaBinding
    private val dataStore: DatastoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePenyewaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCarousel()
        toProfile()
        toAddRoom()
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
    private fun toAddRoom(){
        binding.btnMyRoom.setOnClickListener {
            val intent = Intent(this, AddRoomActivity::class.java)
            startActivity(intent)
        }
    }
}