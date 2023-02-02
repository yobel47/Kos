package com.binar.kos.view.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.dummy.kosDummyData
import com.binar.kos.databinding.ActivityFilterBinding
import com.binar.kos.databinding.ActivityHomeBinding
import com.binar.kos.view.adapter.KosAdapter
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.view.ui.login.LogoutActivity
import com.binar.kos.view.ui.search.SearchActivity
import com.binar.kos.viewmodel.DatastoreViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val dataStore: DatastoreViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showKos(this)
        toProfile()
        binding.searchKosContainer.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showKos(context: Context){
        val adapter = KosAdapter(kosDummyData, context)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.kosTerlarisCard.layoutManager = linearLayoutManager
        binding.kosTerlarisCard.adapter = adapter
    }

    private fun toProfile(){
        dataStore.getLoginState().observe(this){

            if (it){
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