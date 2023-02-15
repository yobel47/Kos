package com.binar.kos.view.ui.filter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.databinding.ActivityFilterBinding
import com.binar.kos.utils.GridSpacingItemDecoration
import com.binar.kos.view.adapter.FilterGeneralAdapter
import com.binar.kos.view.adapter.FilterRoomAdapter
import com.binar.kos.view.ui.searchResult.SearchResultActivity
import com.google.android.material.card.MaterialCardView


class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding

    private lateinit var filterGeneralAdapter: FilterGeneralAdapter
    private lateinit var filterRoomAdapter: FilterRoomAdapter
    private lateinit var filterGeneralList: List<Filter>
    private lateinit var filterRoomList: List<Filter>
    private val filterData = arrayListOf<String>()

    var priceday = 0
    var priceweek = 0
    var pricemonth = 0
    var minprice = 0
    var maxprice = 20000000

    //rules
    var pet = 0
    var couple = 0
    var person = 0
    var check_in = 0
    var check_out = 0
    var children = 0
    var add_electricity = 0
    var smoking_in_room = 1
    var special_student = 0
    var special_employees = 0
    var twf_hours_access = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFilter()
        initFilter()
        onClickType()
        onLongRent()
        onRuleCheck()

        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.etMinimum.editText?.setText(minprice.toString())
        binding.etMaximum.editText?.setText(maxprice.toString())

        binding.btnReset.setOnClickListener {
            onReset()
        }

        binding.btnTerapkan.setOnClickListener {
            onSearch()
        }
    }

    private fun onSearch(){
        val intent = Intent(this, SearchResultActivity::class.java)

        intent.putExtra(PRICEDAY,priceday)
        intent.putExtra(PRICEWEEK,priceweek)
        intent.putExtra(PRICEMONTH,pricemonth)
        intent.putExtra(MINPRICE,binding.etMinimum.editText?.text.toString())
        intent.putExtra(MAXPRICE,binding.etMaximum.editText?.text.toString())
        intent.putExtra(PET,pet)
        intent.putExtra(COUPLE,couple)
        intent.putExtra(PERSON,person)
        intent.putExtra(CHECKIN,check_in)
        intent.putExtra(CHECKOUT,check_out)
        intent.putExtra(CHILDREN,children)
        intent.putExtra(ADDELECTRICITY,add_electricity)
        intent.putExtra(SMOKINGINROOM,smoking_in_room)
        intent.putExtra(SPECIALSTUDENT,special_student)
        intent.putExtra(SPECIALEMPLOYEES,special_employees)
        intent.putExtra(TWFHOURSACCESS,twf_hours_access)
        intent.putExtra(FACILITIESDATA,filterData)
        finish()
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onReset(){
        binding.cvMan.strokeWidth = 3
        binding.cvMan.strokeColor = Color.parseColor("#C7C6CA")
        binding.cvWoman.strokeWidth = 3
        binding.cvWoman.strokeColor = Color.parseColor("#C7C6CA")
        binding.cvManWoman.strokeWidth = 3
        binding.cvManWoman.strokeColor = Color.parseColor("#C7C6CA")
        binding.rbDaily.isChecked = false
        binding.rbWeekly.isChecked = false
        binding.rbMonthly.isChecked = false
        binding.etMinimum.editText?.setText(minprice.toString())
        binding.etMaximum.editText?.setText(maxprice.toString())

        filterData.clear()

        filterGeneralAdapter.notifyDataSetChanged()
        filterRoomAdapter.notifyDataSetChanged()

        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        binding.rvFacilities.layoutManager = layoutManager
        filterGeneralAdapter =
            FilterGeneralAdapter(filterRoomList) { filter, view ->
                onClickFacilities(filter,
                    view)
            }
        binding.rvFacilities.adapter = filterGeneralAdapter

        val layoutManager2: RecyclerView.LayoutManager =
            GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        binding.rvFacilities2.layoutManager = layoutManager2
        filterRoomAdapter =
            FilterRoomAdapter(filterGeneralList) { filter, view -> onClickFacilities(filter, view) }
        binding.rvFacilities2.adapter = filterRoomAdapter

        binding.cb24hours.isChecked = false
        binding.cbMarried.isChecked = false
        binding.cbMax2person.isChecked = false
        binding.cbPet.isChecked = false
        binding.cbWorker.isChecked = false
        binding.cbStudent.isChecked = false
        binding.cbSmokingInRoom.isChecked = false
        binding.cbElecticity.isChecked = false
        binding.cbCheckIn.isChecked = false
        binding.cbCheckOut.isChecked = false

        priceday = 0
        priceweek = 0
        pricemonth = 0
        minprice = 0
        maxprice = 20000000

        //rules
        pet = 0
        couple = 0
        person = 0
        check_in = 0
        check_out = 0
        children = 0
        add_electricity = 0
        smoking_in_room = 1
        special_student = 0
        special_employees = 0
        twf_hours_access = 0

    }



    private fun onRuleCheck(){
        binding.cb24hours.setOnCheckedChangeListener { _, b ->
            twf_hours_access = if(b){
                1
            }else{
                0
            }
        }

        binding.cbMarried.setOnCheckedChangeListener { _, b ->
            couple = if(b){
                1
            }else{
                0
            }
        }

        binding.cbMax2person.setOnCheckedChangeListener { _, b ->
            person = if(b){
                2
            }else{
                0
            }
        }

        binding.cbPet.setOnCheckedChangeListener { _, b ->
            pet = if(b){
                1
            }else{
                0
            }
        }

        binding.cbWorker.setOnCheckedChangeListener { _, b ->
            special_employees = if(b){
                1
            }else{
                0
            }
        }

        binding.cbStudent.setOnCheckedChangeListener { _, b ->
            special_student = if(b){
                1
            }else{
                0
            }
        }

        binding.cbSmokingInRoom.setOnCheckedChangeListener { _, b ->
            smoking_in_room = if(b){
                0
            }else{
                1
            }
        }

        binding.cbElecticity.setOnCheckedChangeListener { _, b ->
            add_electricity = if(b){
                1
            }else{
                0
            }
        }

        binding.cbCheckIn.setOnCheckedChangeListener { _, b ->
            check_in = if(b){
                1
            }else{
                0
            }
        }

        binding.cbCheckOut.setOnCheckedChangeListener { _, b ->
            check_out = if(b){
                1
            }else{
                0
            }
        }
    }

    private fun onLongRent(){
        binding.rbDaily.setOnCheckedChangeListener { _, b ->
            priceday = if(b){
                1
            }else{
                0
            }
        }
        binding.rbWeekly.setOnCheckedChangeListener { _, b ->
            priceweek = if(b){
                1
            }else{
                0
            }
        }
        binding.rbMonthly.setOnCheckedChangeListener { _, b ->
            pricemonth = if(b){
                1
            }else{
                0
            }
        }
    }



    @Suppress("DEPRECATION")
    private fun onClickType() {
        binding.cvMan.setOnClickListener {
            val card = it as MaterialCardView
            if (filterData.contains("tipekostputra")) {
                card.strokeWidth = 3
                card.strokeColor = Color.parseColor("#C7C6CA")
            } else {
                card.strokeWidth = 5
                card.strokeColor = resources.getColor(R.color.primary)
            }
            checkFilter("tipekostputra")
        }
        binding.cvWoman.setOnClickListener {
            val card = it as MaterialCardView
            if (filterData.contains("tipekostputri")) {
                card.strokeWidth = 3
                card.strokeColor = Color.parseColor("#C7C6CA")
            } else {
                card.strokeWidth = 5
                card.strokeColor =  resources.getColor(R.color.primary)
            }
            checkFilter("tipekostputri")
        }
        binding.cvManWoman.setOnClickListener {
            val card = it as MaterialCardView
            if (filterData.contains("tipekostcampur")) {
                card.strokeWidth = 3
                card.strokeColor = Color.parseColor("#C7C6CA")
            } else {
                card.strokeWidth = 5
                card.strokeColor =  resources.getColor(R.color.primary)
            }
            checkFilter("tipekostcampur")
        }
    }

    private fun initFilter() {
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        binding.rvFacilities.layoutManager = layoutManager
        filterGeneralAdapter =
            FilterGeneralAdapter(filterRoomList) { filter, view ->
                onClickFacilities(filter,
                    view)
            }
        binding.rvFacilities.addItemDecoration(GridSpacingItemDecoration(1, 20, 40))
        binding.rvFacilities.adapter = filterGeneralAdapter

        val layoutManager2: RecyclerView.LayoutManager =
            GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false)
        binding.rvFacilities2.layoutManager = layoutManager2
        filterRoomAdapter =
            FilterRoomAdapter(filterGeneralList) { filter, view -> onClickFacilities(filter, view) }
        binding.rvFacilities2.addItemDecoration(GridSpacingItemDecoration(1, 20, 40))
        binding.rvFacilities2.adapter = filterRoomAdapter
    }

    @SuppressLint("ResourceAsColor")
    private fun onClickFacilities(filter: Filter, view: View) {
        var value = ""
        value = when(filter.text){
            "Kasur" -> "bed"
            "Kipas Angin" -> "fan"
            "Kursi" -> "chair"
            "Meja" -> "table"
            "Bantal" -> "pillow"
            "Jendela" -> "window"
            "TV" -> "tvinroom"
            "Lemari Baju" -> "wardrobe"
            "Air Panas" -> "hot_water"
            "Kloset Duduk" -> "seat_toilet"
            "Kloset Jongkok" -> "squat_toilet"
            "Kamar Mandi Dalam" -> "inside_bathroom"
            "Kamar Mandi Luar" -> "outside_bathroom"
            "AC" -> "air_conditioning"
            "Termasuk Listrik" -> "electricity"
            "Setrika" -> "iron"
            "Wifi" -> "wifi"
            "Dapur" -> "kitchen"
            "Laundry" -> "loundry"
            "Mushola" -> "mushola"
            "Dispenser" -> "dispenser"
            "Parkir Mobil" -> "car_parking"
            "Parkir Motor" -> "motorcycle_parking"
            "Parkir Sepeda" -> "bicycle_parking"
            "Ruang Tamu" -> "living_room"
            "Mesin Cuci" -> "washing_machine"
            "Kulkas" -> "refrigerator"
            else -> "nothing"
        }

        val card = view as MaterialCardView
        if (filterData.contains(value)) {
            card.strokeWidth = 3
            card.strokeColor = Color.parseColor("#C7C6CA")
        } else {
            card.strokeWidth = 5
            @Suppress("DEPRECATION")
            card.strokeColor =  resources.getColor(R.color.primary)
        }
        checkFilter(value)
    }

    private fun checkFilter(text: String) {
        if (filterData.contains(text)) {
            filterData.remove(text)
        } else {
            filterData.add(text)
        }
    }

    private fun loadFilter() {
        filterGeneralList = listOf(
            Filter(R.drawable.ic_wifi, "Wifi"),
            Filter(R.drawable.ic_car, "Parkir Mobil"),
            Filter(R.drawable.ic_motorcycle, "Parkir Motor"),
            Filter(R.drawable.ic_bicycle, "Parkir Sepeda"),
            Filter(R.drawable.ic_wash_machine, "Mesin Cuci"),
            Filter(R.drawable.ic_iron, "Setrika"),
            Filter(R.drawable.ic_kitchen, "Dapur"),
            Filter(R.drawable.ic_living_room, "Ruang Tamu"),
            Filter(R.drawable.ic_drinking_water, "Dispenser"),
            Filter(R.drawable.ic_mushola, "Mushola"),
            Filter(R.drawable.ic_refrigerator, "Kulkas"),
            Filter(R.drawable.ic_laundry, "Laundry"),
        )

        filterRoomList = listOf(
            Filter(R.drawable.ic_bathub, "Kamar Mandi Dalam"),
            Filter(R.drawable.ic_ac, "AC"),
            Filter(R.drawable.ic_tv, "TV"),
            Filter(R.drawable.ic_desk, "Meja"),
            Filter(R.drawable.ic_cupboard, "Lemari Baju"),
            Filter(R.drawable.ic_bed, "Kasur"),
            Filter(R.drawable.ic_pillow, "Bantal"),
            Filter(R.drawable.ic_chair, "Kursi"),
            Filter(R.drawable.ic_window, "Jendela"),
            Filter(R.drawable.ic_hot_tub, "Air Panas"),
            Filter(R.drawable.ic_toilet_squad, "Kloset Jongkok"),
            Filter(R.drawable.ic_toilet, "Kloset Duduk"),
            Filter(R.drawable.ic_fan, "Kipas Angin"),
            Filter(R.drawable.ic_lightning, "Termasuk Listrik"),
        )
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    companion object{
        const val PRICEDAY  = "price_day"
        const val PRICEWEEK  = "price_week"
        const val PRICEMONTH  = "price_month"
        const val MINPRICE  = "min_price"
        const val MAXPRICE  = "max_price"
        const val PET  = "pet"
        const val COUPLE  = "couple"
        const val PERSON  = "person"
        const val CHECKIN  = "check_in"
        const val CHECKOUT  = "check_out"
        const val CHILDREN  = "children"
        const val ADDELECTRICITY  = "add_electricity"
        const val SMOKINGINROOM  = "smoking_in_room"
        const val SPECIALSTUDENT  = "special_student"
        const val SPECIALEMPLOYEES  = "special_employees"
        const val TWFHOURSACCESS  = "twf_hours_access"
        const val FACILITIESDATA = "facilities_data"
    }

}