package com.binar.kos.view.ui.searchResult

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.kos.data.remote.response.searchResponse.SearchResponse
import com.binar.kos.databinding.ActivitySearchResultBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.adapter.SearchResultAdapter
import com.binar.kos.view.ui.filter.FilterActivity
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.room.RoomActivity
import com.binar.kos.view.ui.search.SearchActivity
import com.binar.kos.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var searchResultAdapter: SearchResultAdapter

    var params: String? = null
    var tipekostputra: Int = 0
    var tipekostputri: Int = 0
    var tipekostcampur: Int = 0
    var priceday: Int = 0
    var priceweek: Int = 0
    var pricemonth: Int = 0
    var minprice: Int = 0
    var maxprice: Int = 20000000
    var bed: Int = 0
    var fan: Int = 0
    var chair: Int = 0
    var table: Int = 0
    var pillow: Int = 0
    var window: Int = 0
    var tvinroom: Int = 0
    var wardrobe: Int = 0
    var hot_water: Int = 0
    var seat_toilet: Int = 0
    var squat_toilet: Int = 0
    var inside_bathroom: Int = 0
    var outside_bathroom: Int = 0
    var air_conditioning: Int = 0
    var electricity: Int = 0
    var iron: Int = 0
    var wifi: Int = 0
    var kitchen: Int = 0
    var loundry: Int = 0
    var mushola: Int = 0
    var dispenser: Int = 0
    var car_parking: Int = 0
    var motorcycle_parking: Int = 0
    var bicycle_parking: Int = 0
    var living_room: Int = 0
    var tvinlivingroom: Int = 0
    var washing_machine: Int = 0
    var refrigerator: Int = 0
    var pet: Int = 0
    var couple: Int = 0
    var person: Int = 0
    var check_in: Int = 0
    var check_out: Int = 0
    var children: Int = 0
    var add_electricity: Int = 0
    var smoking_in_room: Int = 0
    var special_student: Int = 0
    var special_employees: Int = 1
    var twf_hours_access: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvTypeFilter.visibility = View.GONE
        binding.tvPriceFilter.visibility = View.GONE
        binding.tvDurationFilter.visibility = View.GONE
        binding.tvRuleFilter.visibility = View.GONE
        binding.tvFacilitiesFilter.visibility = View.GONE

        onSetup()


        binding.cvBottom.strokeWidth = 3
        binding.cvBottom.strokeColor = Color.parseColor("#C7C6CA")

        if (!intent.getStringExtra(SearchActivity.SEARCH_TEXT).isNullOrEmpty()) {
            params = intent.getStringExtra(SearchActivity.SEARCH_TEXT)
            if (params!!.isNotEmpty()) {
                binding.etSearch.setText(params.toString())
                params!!.lowercase()
                getData()
            }
        }


        binding.btnFilter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }

        getData()

        onSearch()
        setAdapter()
        onBack()
    }

    private fun onSearch() {
        binding.etSearch.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                params = binding.etSearch.text.toString().lowercase()
                getData()
            }
            false
        })
    }


    private fun onSetup() {
        priceday = intent.getIntExtra(FilterActivity.PRICEDAY, 0)
        priceweek = intent.getIntExtra(FilterActivity.PRICEWEEK, 0)
        pricemonth = intent.getIntExtra(FilterActivity.PRICEMONTH, 0)
        minprice = intent.getIntExtra(FilterActivity.MINPRICE, 0)
        maxprice = intent.getIntExtra(FilterActivity.MAXPRICE, 20000000)
        pet = intent.getIntExtra(FilterActivity.PET, 0)
        couple = intent.getIntExtra(FilterActivity.COUPLE, 0)
        person = intent.getIntExtra(FilterActivity.PERSON, 0)
        check_in = intent.getIntExtra(FilterActivity.CHECKIN, 0)
        check_out = intent.getIntExtra(FilterActivity.CHECKOUT, 0)
        children = intent.getIntExtra(FilterActivity.CHILDREN, 0)
        add_electricity = intent.getIntExtra(FilterActivity.ADDELECTRICITY, 0)
        smoking_in_room = intent.getIntExtra(FilterActivity.SMOKINGINROOM, 1)
        special_student = intent.getIntExtra(FilterActivity.SPECIALSTUDENT, 0)
        special_employees = intent.getIntExtra(FilterActivity.SPECIALEMPLOYEES, 0)
        twf_hours_access = intent.getIntExtra(FilterActivity.TWFHOURSACCESS, 0)


        if (priceday != 0 || priceweek != 0 || pricemonth != 0) {
            binding.tvDurationFilter.visibility = View.VISIBLE
        }
        if (minprice != 0 || maxprice != 20000000) {
            binding.tvPriceFilter.visibility = View.VISIBLE
        }
        if (pet != 0 || couple != 0 || person != 0 || check_in != 0 || check_out != 0
            || children != 0 || add_electricity != 0 || smoking_in_room != 1 || special_student != 0 || special_employees != 0
            || twf_hours_access != 0
        ){
            binding.tvRuleFilter.visibility = View.VISIBLE
        }


        val filterData = intent.getStringArrayListExtra(FilterActivity.FACILITIESDATA)
        if (!filterData.isNullOrEmpty()) {
            if (filterData.contains("tipekostputra")) {
                tipekostputra = 1
                binding.tvTypeFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("tipekostputri")) {
                tipekostputri = 1
                binding.tvTypeFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("tipekostcampur")) {
                tipekostcampur = 1
                binding.tvTypeFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("bed")) {
                bed = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("fan")) {
                fan = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("chair")) {
                chair = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("table")) {
                table = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("pillow")) {
                pillow = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("window")) {
                window = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("tvinroom")) {
                tvinroom = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("wardrobe")) {
                wardrobe = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("hot_water")) {
                hot_water = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("seat_toilet")) {
                seat_toilet = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("squat_toilet")) {
                squat_toilet = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("inside_bathroom")) {
                inside_bathroom = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("outside_bathroom")) {
                outside_bathroom = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("air_conditioning")) {
                air_conditioning = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("electricity")) {
                electricity = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("iron")) {
                iron = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("wifi")) {
                wifi = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("kitchen")) {
                kitchen = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("loundry")) {
                loundry = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("mushola")) {
                mushola = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("dispenser")) {
                dispenser = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("car_parking")) {
                car_parking = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("motorcycle_parking")) {
                motorcycle_parking = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("bicycle_parking")) {
                bicycle_parking = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("living_room")) {
                living_room = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("washing_machine")) {
                washing_machine = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
            if (filterData.contains("refrigerator")) {
                refrigerator = 1
                binding.tvFacilitiesFilter.visibility = View.VISIBLE
            }
        }
    }


    private fun onBack() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getData() {
        searchViewModel.searchRoom(
            params,
            tipekostputra,
            tipekostputri,
            tipekostcampur,
            priceday,
            priceweek,
            pricemonth,
            minprice,
            maxprice,
            bed,
            fan,
            chair,
            table,
            pillow,
            window,
            tvinroom,
            wardrobe,
            hot_water,
            seat_toilet,
            squat_toilet,
            inside_bathroom,
            outside_bathroom,
            air_conditioning,
            electricity,
            iron,
            wifi,
            kitchen,
            loundry,
            mushola,
            dispenser,
            car_parking,
            motorcycle_parking,
            bicycle_parking,
            living_room,
            tvinlivingroom,
            washing_machine,
            refrigerator,
            pet,
            couple,
            person,
            check_in,
            check_out,
            children,
            add_electricity,
            smoking_in_room,
            special_student,
            special_employees,
            twf_hours_access
        ).observe(this@SearchResultActivity) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.pb.isVisible = true
                    binding.tvEmptyRv.isVisible = false
                    binding.rvSearchResult.isVisible = false
                }
                Status.SUCCESS -> {
                    binding.pb.isVisible = false
                    if (result.data!!.isEmpty()) {
                        binding.tvEmptyRv.isVisible = true
                    } else {
                        binding.rvSearchResult.isVisible = true
                        searchResultAdapter.submitList(result.data)
                    }
                }
                Status.ERROR -> {
                    binding.pb.isVisible = false
                    binding.tvEmptyRv.isVisible = true
                    binding.tvEmptyRv.text = result.message
                }
            }
        }
    }

    private fun setAdapter() {
        binding.rvSearchResult.apply {
            searchResultAdapter = SearchResultAdapter(
                clickListener = {
                    onClick(it)
                }
            )
            layoutManager = LinearLayoutManager(context)
            adapter = searchResultAdapter
        }
    }

    private fun onClick(searchResponse: SearchResponse) {
        val intent = Intent(this, RoomActivity::class.java)
        intent.putExtra(HomeActivity.ID_ROOM,searchResponse.id.toString())
        startActivity(intent)
    }
}