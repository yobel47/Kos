package com.binar.kos.view.ui.filter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.databinding.ActivityFilterBinding
import com.binar.kos.utils.GridSpacingItemDecoration
import com.binar.kos.view.adapter.FilterGeneralAdapter
import com.binar.kos.view.adapter.FilterRoomAdapter

class FilterActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityFilterBinding

    private lateinit var filterGeneralAdapter: FilterGeneralAdapter
    private lateinit var filterRoomAdapter: FilterRoomAdapter
    private lateinit var filterGeneralList : List<Filter>
    private lateinit var filterRoomList : List<Filter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFilter()

//        binding.slPrice.addOnChangeListener { rangeSlider, _, _ ->
//            val values = rangeSlider.values
//            binding.etMinimum.editText?.setText("${values[0].toInt()}")
//            binding.etMaximum.editText?.setText("${values[1].toInt()}")
//        }

//        binding.etMaximum.editText?.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                if(binding.etMaximum.editText?.text.toString().toFloat() <= 20000000f) {
//                    binding.slPrice.valueTo = binding.etMaximum.editText?.text.toString().toFloat()
//                }
//            }
//        })
//
//        binding.etMinimum.editText?.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                if(binding.etMinimum.editText?.text.toString().toFloat() >= 20000f){
//                    binding.slPrice.valueFrom = binding.etMinimum.editText?.text.toString().toFloat()
//                }
//            }
//        })

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        binding.rvFacilities.layoutManager = layoutManager
        filterGeneralAdapter = FilterGeneralAdapter(filterGeneralList) { filter -> onClick(filter) }
        binding.rvFacilities.addItemDecoration(GridSpacingItemDecoration(1,20, 40))
        binding.rvFacilities.adapter = filterGeneralAdapter

        val layoutManager2: RecyclerView.LayoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        binding.rvFacilities2.layoutManager = layoutManager2
        filterRoomAdapter = FilterRoomAdapter(filterRoomList) { filter -> onClick(filter) }
        binding.rvFacilities2.addItemDecoration(GridSpacingItemDecoration(1,20, 40))
        binding.rvFacilities2.adapter = filterRoomAdapter
    }

    private fun onClick(filter: Filter){
        Toast.makeText(this, filter.text, Toast.LENGTH_SHORT).show()
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