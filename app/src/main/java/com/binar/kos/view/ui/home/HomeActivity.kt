package com.binar.kos.view.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.binar.kos.view.ui.favorit.FavoritActivity
import com.binar.kos.view.ui.filter.FilterActivity
import com.binar.kos.view.ui.history.HistoryActivity
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.view.ui.logout.LogoutActivity
import com.binar.kos.view.ui.notification.NotificationActivity
import com.binar.kos.view.ui.search.SearchActivity
import com.binar.kos.view.ui.searchResult.SearchResultActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
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

        fetchPromoRooms()
        fetchAllRooms()
        setCarousel()
        toProfile()
        toNotif()
        toFavorit()
        toHistory()

        binding.etSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    Pair(binding.etSearch as View, "search")
                )
            startActivity(intent, optionsCompat.toBundle())
        }

        binding.btnSearchFilter.setOnClickListener {
            toFilter()
        }

        binding.filterButton.setOnClickListener {
            toFilter()
        }

        binding.btnJakarta.setOnClickListener {
            toSearchResult("Jakarta")
        }
        binding.btnBandung.setOnClickListener {
            toSearchResult("Bandung")
        }
        binding.btnSurabaya.setOnClickListener {
            toSearchResult("Surabaya")
        }
        binding.btnYogyakarta.setOnClickListener {
            toSearchResult("Yogyakarta")
        }

    }

    private fun toSearchResult(text: String) {
        val intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_TEXT, text)
        startActivity(intent)
    }

    private fun toFilter() {
        val intent = Intent(this, FilterActivity::class.java)
        startActivity(intent)
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

    private fun showKos(context: Context, kosList: ArrayList<Kos>) {
        val adapter = KosAdapter(kosList, context)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.kosTerlarisCard.layoutManager = linearLayoutManager
        binding.kosTerlarisCard.adapter = adapter
    }

    private fun fetchPromoRooms() {
        homeViewModel.getPromoRooms().observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.rvPromo.isVisible = false
                    binding.pbPromo.isVisible = true
                    binding.tvEmptyRvPromo.isVisible = false
                }
                Status.SUCCESS -> {
                    binding.pbPromo.isVisible = false
                    binding.rvPromo.isVisible = true
                    val response = result.data
                    if (response!!.isEmpty()) {
                        binding.tvEmptyRv.isVisible = true
                    }else{
                        val adapter = KosAdapter(response, this)
                        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        binding.rvPromo.layoutManager = linearLayoutManager
                        binding.rvPromo.adapter = adapter
                    }
                }
                Status.ERROR -> {
                    binding.pbPromo.isVisible = false
                    binding.tvEmptyRvPromo.text = result.message
                }
            }

        }
    }

    private fun fetchAllRooms() {
        homeViewModel.getAllRooms().observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.kosTerlarisCard.isVisible = false
                    binding.pb.isVisible = true
                    binding.tvEmptyRv.isVisible = false
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
                    binding.pb.isVisible = false
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

    private fun toNotif() {
        dataStore.getLoginState().observe(this) {
            if (it) {
                binding.notificationButton.setOnClickListener {
                    val intent = Intent(this, NotificationActivity::class.java)
                    startActivity(intent)
                }
            } else {
                binding.notificationButton.setOnClickListener {
                    Snackbar.make(binding.root, "Anda belum login", Snackbar.LENGTH_LONG)
                        .setAction("Login") {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }.show()
                }
            }
        }
    }

    private fun toFavorit() {
        dataStore.getLoginState().observe(this) {
            if (it) {
                binding.btnFavorite.setOnClickListener {
                    val intent = Intent(this, FavoritActivity::class.java)
                    startActivity(intent)
                }
            } else {
                binding.btnFavorite.setOnClickListener {
                    Snackbar.make(binding.root, "Anda belum login", Snackbar.LENGTH_LONG)
                        .setAction("Login") {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }.show()
                }
            }
        }
    }
    private fun toHistory() {
        dataStore.getLoginState().observe(this) {
            if (it) {
                binding.btnHistory.setOnClickListener {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                }
            } else {
                binding.btnHistory.setOnClickListener {
                    Snackbar.make(binding.root, "Anda belum login", Snackbar.LENGTH_LONG)
                        .setAction("Login") {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }.show()
                }
            }
        }
    }

    companion object {
        const val ID_ROOM = "id_room"
    }
}