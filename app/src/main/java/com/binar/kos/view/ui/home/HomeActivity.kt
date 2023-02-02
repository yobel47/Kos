package com.binar.kos.view.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.kos.data.local.entity.Kos
import com.binar.kos.databinding.ActivityHomeBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.adapter.KosAdapter
import com.binar.kos.view.ui.login.LogoutActivity
import com.binar.kos.view.ui.search.SearchActivity
import com.binar.kos.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchAllRooms()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchKosContainer.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, LogoutActivity::class.java)
            startActivity(intent)
        }
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
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    val response = result.data
                    Log.e("HEYYY", result.data.toString())
                    showKos(this, response!!)
                }
                Status.ERROR -> {

                }
            }

        }
    }
}