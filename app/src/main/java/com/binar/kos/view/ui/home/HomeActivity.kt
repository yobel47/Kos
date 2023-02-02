package com.binar.kos.view.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.kos.R
import com.binar.kos.data.local.entity.Kos
import com.binar.kos.databinding.ActivityHomeBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.adapter.KosAdapter
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.view.ui.login.LogoutActivity
import com.binar.kos.view.ui.search.SearchActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.HomeViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchAllRooms()
        setCarousel()
        toProfile()

        binding.etSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    Pair(binding.etSearch as View, "search")
                )
            startActivity(intent, optionsCompat.toBundle())
        }
    }

    private fun setCarousel() {
        binding.topBanner.registerLifecycle(lifecycle)
        binding.topBanner.showIndicator = true
        binding.topBanner.showNavigationButtons = false
        binding.topBanner.showTopShadow = false
        binding.topBanner.showBottomShadow = false
        binding.topBanner.autoPlay = true
        binding.topBanner.autoPlayDelay = 3000

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

        binding.topBanner.setData(list)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun showKos(context: Context, kosList: ArrayList<Kos>) {
        val adapter = KosAdapter(kosList, context)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.kosTerlarisCard.layoutManager = linearLayoutManager
        binding.kosTerlarisCard.adapter = adapter
    }

    private fun fetchAllRooms() {
        homeViewModel.getAllRooms().observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.kosTerlarisCard.isVisible = false
                    binding.pb.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.pb.isVisible = false
                    binding.kosTerlarisCard.isVisible = true
                    val response = result.data
                    if (response!!.isEmpty()) {
                        binding.tvEmptyRv.isVisible = true
                    }
                    showKos(this, response)
                }
                Status.ERROR -> {
                    binding.tvEmptyRv.text = result.message
                }
            }

        }
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
}