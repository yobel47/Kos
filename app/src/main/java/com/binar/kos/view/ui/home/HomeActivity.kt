package com.binar.kos.view.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.dummy.kosDummyData
import com.binar.kos.view.adapter.KosAdapter

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        showKos(this)
    }

    private fun showKos(context: Context){
        val adapter = KosAdapter(kosDummyData, context)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val kosTerlarisCard = findViewById<RecyclerView>(R.id.kosTerlarisCard)
        kosTerlarisCard.layoutManager = linearLayoutManager
        kosTerlarisCard.adapter = adapter
    }
}