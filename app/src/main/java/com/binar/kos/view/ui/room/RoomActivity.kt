package com.binar.kos.view.ui.room

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.local.entity.Review
import com.binar.kos.data.remote.response.roomResponse.ImageUrlItem
import com.binar.kos.data.remote.response.roomResponse.RoomResponse
import com.binar.kos.databinding.ActivityRoomBinding
import com.binar.kos.databinding.BottomsheetFacilityBinding
import com.binar.kos.databinding.BottomsheetImageBinding
import com.binar.kos.utils.*
import com.binar.kos.view.adapter.FilterGeneralAdapter
import com.binar.kos.view.adapter.FilterRoomAdapter
import com.binar.kos.view.adapter.RoomAdapter.BottomFacilitiesRoomAdapter
import com.binar.kos.view.adapter.RoomAdapter.FacilitiesRoomAdapter
import com.binar.kos.view.adapter.RoomAdapter.ReviewRoomAdapter
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.viewmodel.RoomViewModel
import com.binar.kos.viewmodel.SearchViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var bindingFacilityBottomsheet: BottomsheetFacilityBinding
    private lateinit var bindingImageBottomsheet: BottomsheetImageBinding
    private val roomViewModel: RoomViewModel by viewModel()
    private lateinit var facilitiesRoomAdapter: FacilitiesRoomAdapter
    private lateinit var bottomFacilitiesRoomAdapter: BottomFacilitiesRoomAdapter
    private lateinit var reviewRoomAdapter: ReviewRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        if (Build.VERSION.SDK_INT in 19..20) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
//        }
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
//        window.statusBarColor = Color.TRANSPARENT

        binding.btnVideo.isVisible = false

        val roomId = intent.getStringExtra(HomeActivity.ID_ROOM)
        if (roomId != null) {
            if(roomId.isNotEmpty()){
                getData(roomId)
            }
        }
        setupBottomSheet()

    }


    private fun getData(id: String) {
        roomViewModel.getRoomDetail(id).observe(this@RoomActivity) { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading(this)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    mappingData(result.data!!)
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun mappingData(data: RoomResponse) {
        binding.tvTitleRoom.text = data.title.toString().toCapital()
        binding.tvTypeRoom.text = "Kost ${data.type.toString().toCapital()}"
        binding.tvLocationRoom.text = "${data.address?.street.toString().toCapital()}, ${
            data.address?.village.toString().toCapital()
        }" +
                ", ${data.address?.district.toString().toCapital()}, ${
                    data.address?.city.toString().toCapital()
                }" +
                ", ${data.address?.province.toString().toCapital()}"
        binding.tvProfileName.text = data.createdBy.toString().toCapital()
        if (data.discount?.isDiscount == true) {
            val price = data.price?.costMonth!!.toInt()
            val discount = data.discount.discountPercentage!!.toInt()
            val discountPrice = price / (discount * 100)
            val truePrice = price - discountPrice
            binding.tvDiscountPrice.text = price.toRp()
            binding.tvDiscountPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvDiscount.text = "$discount%"
            binding.tvPrice.text = truePrice.toRp()
        } else {
            binding.tvPrice.text = data.price?.costMonth!!.toInt().toRp()
            binding.tvDiscountPrice.text = ""
            binding.tvDiscount.text = ""
        }

        binding.tvAbout.text = data.description.toString()

        binding.tvLocation.text = "${data.address?.street.toString().toCapital()}, ${
            data.address?.village.toString().toCapital()
        }" +
                ", ${data.address?.district.toString().toCapital()}, ${
                    data.address?.city.toString().toCapital()
                }" +
                ", ${data.address?.province.toString().toCapital()}"

        setupRvFacilities(data)
    }

    private fun setupRvFacilities(data: RoomResponse) {
        val facilitiesRoomData = arrayListOf<Filter>()
        if (data.facilities?.insideBathroom == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_bathub, "Kamar Mandi Dalam"))
        }
        if (data.facilities?.tvinroom == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_tv, "TV"))
        }
        if (data.facilities?.airConditioning == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_ac, "AC"))
        }
        if (data.facilities?.chair == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_chair, "Kursi"))
        }
        if (data.facilities?.fan == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_fan, "Kipas Angin"))
        }
        if (data.facilities?.window == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_window, "Jendela"))
        }
        if (data.facilities?.table == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_desk, "Meja"))
        }
        if (data.facilities?.hotWater == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_hot_tub, "Air Panas"))
        }
        if (data.facilities?.wardrobe == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_cupboard, "Lemari Baju"))
        }
        if (data.facilities?.seatToilet == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_toilet, "Kloset Duduk"))
        }
        if (data.facilities?.bed == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_bed, "Kasur"))
        }
        if (data.facilities?.squatToilet == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_toilet_squad, "Kloset Jongkok"))
        }
        if (data.facilities?.pillow == "1") {
            facilitiesRoomData.add(Filter(R.drawable.ic_pillow, "Bantal"))
        }

        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rvFacilitiesRoom.layoutManager = layoutManager
        facilitiesRoomAdapter =
            FacilitiesRoomAdapter(facilitiesRoomData)
        binding.rvFacilitiesRoom.adapter = facilitiesRoomAdapter

        val generalFacilitiesData = arrayListOf<Filter>()
        if (data.facilities?.wifi == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_wifi, "Wifi"))
        }
        if (data.facilities?.livingRoom == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_living_room, "Ruang Tamu"))
        }
        if (data.facilities?.kitchen == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_kitchen, "Dapur"))
        }
        if (data.facilities?.tvinroom == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_tv, "TV"))
        }
        if (data.facilities?.refrigerator == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_refrigerator, "Kulkas"))
        }
        if (data.facilities?.washingMachine == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_wash_machine, "Mesin Cuci"))
        }
        if (data.facilities?.iron == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_iron, "Setrika"))
        }
        if (data.facilities?.dispenser == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_drinking_water, "Dispenser"))
        }
        if (data.facilities?.carParking == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_car, "Parkir Mobil"))
        }
        if (data.facilities?.motorcycleParking == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_motorcycle, "Parkir Motor"))
        }
        if (data.facilities?.mushola == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_mushola, "Mushola"))
        }
        if (data.facilities?.loundry == "1") {
            generalFacilitiesData.add(Filter(R.drawable.ic_laundry, "Laundry"))
        }
        val layoutManager2: RecyclerView.LayoutManager =
            LinearLayoutManager(this)
        bindingFacilityBottomsheet.rvFacilitiesGeneral.layoutManager = layoutManager2
        bottomFacilitiesRoomAdapter =
            BottomFacilitiesRoomAdapter(generalFacilitiesData)
        bindingFacilityBottomsheet.rvFacilitiesGeneral.adapter = bottomFacilitiesRoomAdapter

        val spesificationData = arrayListOf<Filter>()
        if (data.electricity == true) {
            spesificationData.add(Filter(R.drawable.ic_lightning, "Termasuk listrik"))
        }
        if (data.wide.toString().isNotEmpty()) {
            spesificationData.add(Filter(R.drawable.ic_baseline_photo_size_select_small_24,
                "${data.wide.toString()} meter"))
        }

        val layoutManager3: RecyclerView.LayoutManager =
            LinearLayoutManager(this)
        bindingFacilityBottomsheet.rvSpesificationRoom.layoutManager = layoutManager3
        bottomFacilitiesRoomAdapter =
            BottomFacilitiesRoomAdapter(spesificationData)
        bindingFacilityBottomsheet.rvSpesificationRoom.adapter = bottomFacilitiesRoomAdapter

        val facilitiesData = arrayListOf<Filter>()
        if (data.facilities?.airConditioning == "1") {
            facilitiesData.add(Filter(R.drawable.ic_ac, "AC"))
        }
        if (data.facilities?.table == "1") {
            facilitiesData.add(Filter(R.drawable.ic_desk, "Meja"))
        }
        if (data.facilities?.wardrobe == "1") {
            facilitiesData.add(Filter(R.drawable.ic_cupboard, "Lemari Baju"))
        }
        if (data.facilities?.pillow == "1") {
            facilitiesData.add(Filter(R.drawable.ic_pillow, "Bantal"))
        }
        if (data.facilities?.bed == "1") {
            facilitiesData.add(Filter(R.drawable.ic_bed, "Kasur"))
        }
        if (data.facilities?.tvinroom == "1") {
            facilitiesData.add(Filter(R.drawable.ic_tv, "TV"))
        }
        if (data.facilities?.chair == "1") {
            facilitiesData.add(Filter(R.drawable.ic_chair, "Kursi"))
        }
        if (data.facilities?.window == "1") {
            facilitiesData.add(Filter(R.drawable.ic_window, "Jendela"))
        }
        if (data.facilities?.insideBathroom == "1") {
            facilitiesData.add(Filter(R.drawable.ic_bathub, "Kamar Mandi Dalam"))
        }
        if (data.facilities?.hotWater == "1") {
            facilitiesData.add(Filter(R.drawable.ic_hot_tub, "Air Panas"))
        }
        if (data.facilities?.fan == "1") {
            facilitiesData.add(Filter(R.drawable.ic_fan, "Kipas Angin"))
        }
        if (data.facilities?.seatToilet == "1") {
            facilitiesData.add(Filter(R.drawable.ic_toilet, "Kloset Duduk"))
        }
        if (data.facilities?.squatToilet == "1") {
            facilitiesData.add(Filter(R.drawable.ic_toilet_squad, "Kloset Jongkok"))
        }

        val layoutManager4: RecyclerView.LayoutManager =
            LinearLayoutManager(this)
        bindingFacilityBottomsheet.rvFacilitiesRoom.layoutManager = layoutManager4
        bottomFacilitiesRoomAdapter =
            BottomFacilitiesRoomAdapter(facilitiesData)
        bindingFacilityBottomsheet.rvFacilitiesRoom.adapter = bottomFacilitiesRoomAdapter

        val rulesData = arrayListOf<Filter>()
        if (data.rules?.pet == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Boleh Bawa Hewan"))
        } else {
            rulesData.add(Filter(R.drawable.ic_ac, "Dilarang Bawa Hewan"))
        }
        if (data.rules?.couple == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Boleh Pasutri"))
        } else {
            rulesData.add(Filter(R.drawable.ic_ac, "Tidak Boleh Pasutri"))
        }
        if (data.rules?.person == "2") {
            rulesData.add(Filter(R.drawable.ic_ac, "Maks 2 orang/kamar"))
        }
        if (data.rules?.checkIn == "2") {
            rulesData.add(Filter(R.drawable.ic_ac, "Check in jam 14.00  (sewa harian & mingguan)"))
        }
        if (data.rules?.children == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Boleh Bawa Anak"))
        } else {
            rulesData.add(Filter(R.drawable.ic_ac, "Dilarang Bawa Anak"))
        }
        if (data.rules?.checkOut == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Check out jam 12.00  (sewa harian & mingguan)"))
        }
        if (data.rules?.addElectricity == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Tambah biaya untuk elektronik"))
        } else {
            rulesData.add(Filter(R.drawable.ic_ac, "Tidak ada tambahan biaya untuk elektronik"))
        }
        if (data.rules?.smokingInRoom == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Boleh merokok di kamar"))
        } else {
            rulesData.add(Filter(R.drawable.ic_ac, "Dilarang merokok di kamar"))
        }
        if (data.rules?.specialStudent == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Murid Spesial"))
        } else {
            rulesData.add(Filter(R.drawable.ic_ac, "Bukan Murid Spesial"))
        }
        if (data.rules?.specialEmployee == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Karyawan Spesial"))
        } else {
            rulesData.add(Filter(R.drawable.ic_ac, "Bukan Karyawan Spesial"))
        }
        if (data.rules?.twfHoursAccess == "1") {
            rulesData.add(Filter(R.drawable.ic_ac, "Ini jam jaman pokoknya"))
        }

        val layoutManager5: RecyclerView.LayoutManager =
            LinearLayoutManager(this)
        binding.rvRuleRoom.layoutManager = layoutManager5
        bottomFacilitiesRoomAdapter =
            BottomFacilitiesRoomAdapter(rulesData)
        binding.rvRuleRoom.adapter = bottomFacilitiesRoomAdapter

        val reviewData = listOf(
            Review("Ngekost di sini terjamin ga zonk karena sesuai dengan informasi yang diberikan penyewa kost.\n" +
                    "Saya sangat nyaman kost di sini juga dalam menggunakan fasilitas Omah Kos karena terjamin keamanan dan kenyamanannya.",
                "Kimmy Jayanti",
                "1 Minggu yang lalu"),
            Review("Ibu kosan baik banget, kosan selalu bersih dari lorong, fasilitas bersama (lorong, tempat ngejemur, tempat sampah dsb), kalau masak selalu nawarin,warung cemilan & laundry deket. ",
                "Arlino Gilldano",
                "1 Minggu yang lalu"),
            Review("Pemilik merhatiin kenyamanan penghuni, air kotor langsung dicari tukang untuk benerin malem2. Kamar rapi, dinding bersih, kasur ga bau bekas penghuni, kamar mandi juga, intinya siap huni.",
                "Budi Setiawan",
                "1 Minggu yang lalu"),
        )

        val layoutManager6: RecyclerView.LayoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        binding.rvReviewRoom.layoutManager = layoutManager6
        reviewRoomAdapter = ReviewRoomAdapter(reviewData)
        binding.rvReviewRoom.addItemDecoration(GridSpacingItemDecoration(1, 20, 40))
        binding.rvReviewRoom.adapter = reviewRoomAdapter

        setCarousel(data.imageUrl!!)

        val kosImageResponse = data.imageUrl[0]
        val requestOptions = RequestOptions().placeholder(R.drawable.kos_dummy_image)
        Glide.with(this).load(kosImageResponse).apply(requestOptions).skipMemoryCache(true)
            .into(binding.ivCarouselRoom)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupBottomSheet() {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val height = displayMetrics.heightPixels

        bindingFacilityBottomsheet = BottomsheetFacilityBinding.inflate(LayoutInflater.from(this))
        bindingImageBottomsheet = BottomsheetImageBinding.inflate(LayoutInflater.from(this))

        val bottomSheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val bottomSheetDialog2 = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)

        bottomSheetDialog.setContentView(bindingFacilityBottomsheet.root)
        bottomSheetDialog2.setContentView(bindingImageBottomsheet.root)

        val bottomSheetBehavior: BottomSheetBehavior<View> =
            BottomSheetBehavior.from(bindingFacilityBottomsheet.root.parent as View)
        val bottomSheetBehavior2: BottomSheetBehavior<View> =
            BottomSheetBehavior.from(bindingImageBottomsheet.root.parent as View)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior2.state = BottomSheetBehavior.STATE_HIDDEN

        @Suppress("DEPRECATION")
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(view: View, i: Int) {
                // do something when state changes
                when (i) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        bindingFacilityBottomsheet.root.background =
                            getDrawable(R.drawable.rounded_bottom_sheet)
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bindingFacilityBottomsheet.root.background = getDrawable(R.color.background)
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bindingFacilityBottomsheet.root.background =
                            getDrawable(R.drawable.rounded_bottom_sheet)
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                        bottomSheetDialog.dismiss()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(view: View, v: Float) {
            }
        })

        bindingFacilityBottomsheet.btnClose.setOnClickListener {
            bindingFacilityBottomsheet.root.background =
                getDrawable(R.drawable.rounded_bottom_sheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheetDialog.dismiss()
        }

        bindingImageBottomsheet.btnClose.setOnClickListener {
            bindingImageBottomsheet.root.background =
                getDrawable(R.drawable.rounded_bottom_sheet)
            bottomSheetBehavior2.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheetDialog2.dismiss()
        }

        binding.btnSeeImage.setOnClickListener {
            bottomSheetDialog2.show()
            bottomSheetBehavior2.state = BottomSheetBehavior.STATE_EXPANDED
            bindingImageBottomsheet.root.minimumHeight = height + 52
            bottomSheetBehavior2.peekHeight = height + 52
            bindingImageBottomsheet.root.layoutParams.height = height + 52
        }

        binding.btnMoreFacilities.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bindingFacilityBottomsheet.root.minimumHeight = height + 52
            bottomSheetBehavior.peekHeight = (height * 0.4).toInt()
            bottomSheetDialog.show()
        }
    }

    private fun setCarousel(data: List<ImageUrlItem?>) {
        bindingImageBottomsheet.imgCarousel.registerLifecycle(lifecycle)
        bindingImageBottomsheet.imgCarousel.showIndicator = true
        bindingImageBottomsheet.imgCarousel.showNavigationButtons = true
        bindingImageBottomsheet.imgCarousel.showTopShadow = false
        bindingImageBottomsheet.imgCarousel.showBottomShadow = false

        val list = mutableListOf<CarouselItem>()
        data.map {
            list.add(
                CarouselItem(
                    imageUrl = it?.url
                )
            )
        }
        bindingImageBottomsheet.imgCarousel.setData(list)
    }

//    private fun setWindowFlag(bits: Int, on: Boolean) {
//        val win = window
//        val winParams = win.attributes
//        if (on) {
//            winParams.flags = winParams.flags or bits
//        } else {
//            winParams.flags = winParams.flags and bits.inv()
//        }
//        win.attributes = winParams
//    }
}