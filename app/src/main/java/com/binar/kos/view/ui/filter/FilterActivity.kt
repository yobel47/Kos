package com.binar.kos.view.ui.filter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.databinding.ActivityFilterBinding
import com.binar.kos.utils.GridSpacingItemDecoration
import com.binar.kos.view.adapter.FilterGeneralAdapter
import com.binar.kos.view.adapter.FilterRoomAdapter
import com.google.android.material.card.MaterialCardView


class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding

    private lateinit var filterGeneralAdapter: FilterGeneralAdapter
    private lateinit var filterRoomAdapter: FilterRoomAdapter
    private lateinit var filterGeneralList: List<Filter>
    private lateinit var filterRoomList: List<Filter>
    private val filterData = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFilter()
        initFilter()
        onClickType()
    }

    private fun onClickType() {
        binding.cvMan.setOnClickListener {
            val card = it as MaterialCardView
            if (filterData.contains("Man")) {
                card.strokeWidth = 3
                card.strokeColor = Color.parseColor("#C7C6CA")
            } else {
                card.strokeWidth = 5
                card.strokeColor = resources.getColor(R.color.primary)
            }
            checkFilter("Man")
        }
        binding.cvWoman.setOnClickListener {
            val card = it as MaterialCardView
            if (filterData.contains("Woman")) {
                card.strokeWidth = 3
                card.strokeColor = Color.parseColor("#C7C6CA")
            } else {
                card.strokeWidth = 5
                card.strokeColor =  resources.getColor(R.color.primary)
            }
            checkFilter("Woman")
        }
        binding.cvManWoman.setOnClickListener {
            val card = it as MaterialCardView
            if (filterData.contains("Mix")) {
                card.strokeWidth = 3
                card.strokeColor = Color.parseColor("#C7C6CA")
            } else {
                card.strokeWidth = 5
                card.strokeColor =  resources.getColor(R.color.primary)
            }
            checkFilter("Mix")
        }
    }

    private fun initFilter() {
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        binding.rvFacilities.layoutManager = layoutManager
        filterGeneralAdapter =
            FilterGeneralAdapter(filterGeneralList) { filter, view ->
                onClickFacilities(filter,
                    view)
            }
        binding.rvFacilities.addItemDecoration(GridSpacingItemDecoration(1, 20, 40))
        binding.rvFacilities.adapter = filterGeneralAdapter

        val layoutManager2: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        binding.rvFacilities2.layoutManager = layoutManager2
        filterRoomAdapter =
            FilterRoomAdapter(filterRoomList) { filter, view -> onClickFacilities(filter, view) }
        binding.rvFacilities2.addItemDecoration(GridSpacingItemDecoration(1, 20, 40))
        binding.rvFacilities2.adapter = filterRoomAdapter
    }

    @SuppressLint("ResourceAsColor")
    private fun onClickFacilities(filter: Filter, view: View) {
        val card = view as MaterialCardView
        if (filterData.contains(filter.text)) {
            card.strokeWidth = 3
            card.strokeColor = Color.parseColor("#C7C6CA")
        } else {
            card.strokeWidth = 5
            card.strokeColor =  resources.getColor(R.color.primary)
        }
        checkFilter(filter.text)
    }

    fun checkFilter(text: String) {
        if (filterData.contains(text)) {
            filterData.remove(text)
        } else {
            filterData.add(text)
        }
        Toast.makeText(this, filterData.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun loadFilter() {
        filterGeneralList = listOf(
            Filter(R.drawable.ic_bathub, "Kamar Mandi Dalam"),
            Filter(R.drawable.ic_tv, "TV"),
            Filter(R.drawable.ic_ac, "AC"),
            Filter(R.drawable.ic_chair, "Kursi"),
            Filter(R.drawable.ic_fan, "Kipas Angin"),
            Filter(R.drawable.ic_window, "Jendela"),
            Filter(R.drawable.ic_desk, "Meja"),
            Filter(R.drawable.ic_hot_tub, "Air Panas"),
            Filter(R.drawable.ic_cupboard, "Lemari Baju"),
            Filter(R.drawable.ic_toilet, "Kloset Duduk"),
            Filter(R.drawable.ic_bed, "Kasur"),
            Filter(R.drawable.ic_toilet_squad, "Kloset Jongkok"),
            Filter(R.drawable.ic_pillow, "Bantal"),
            Filter(R.drawable.ic_lightning, "Termasuk Listrik"),
        )

        filterRoomList = listOf(
            Filter(R.drawable.ic_wifi, "Wifi"),
            Filter(R.drawable.ic_living_room, "Ruang Tamu"),
            Filter(R.drawable.ic_motorcycle, "Parkir Motor"),
            Filter(R.drawable.ic_drinking_water, "Dispenser"),
            Filter(R.drawable.ic_car, "Parkir Mobil"),
            Filter(R.drawable.ic_mushola, "Mushola"),
            Filter(R.drawable.ic_wash_machine, "Mesin Cuci"),
            Filter(R.drawable.ic_tv, "TV"),
            Filter(R.drawable.ic_iron, "Setrika"),
            Filter(R.drawable.ic_refrigerator, "Kulkas"),
            Filter(R.drawable.ic_laundry, "Laundry"),
            Filter(R.drawable.ic_kitchen, "Dapur"),
        )
    }

}