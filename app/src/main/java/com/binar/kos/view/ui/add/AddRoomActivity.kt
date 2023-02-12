package com.binar.kos.view.ui.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.*
import android.view.View.GONE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.binar.kos.R
import com.binar.kos.data.remote.request.AddRoomRequest
import com.binar.kos.databinding.ActivityAddRoomBinding
import com.binar.kos.databinding.CardChooseImageBinding
import com.binar.kos.databinding.SentDialog2Binding
import com.binar.kos.databinding.SentDialogBinding
import com.binar.kos.utils.*
import com.binar.kos.view.ui.homePenyewa.HomePenyewaActivity
import com.binar.kos.view.ui.login.LoginActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.RoomViewModel
import com.bumptech.glide.Glide
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*


class AddRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRoomBinding
    private lateinit var isdialogg: AlertDialog
    private lateinit var isdialoggg: AlertDialog
    private val roomViewModel: RoomViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()

    //data
    var title = ""
    var type = ""
    var stock = ""
    var description = ""
    var wide = ""

    //facilities
    //room facilities
    var bed = "0"
    var fan = "0"
    var chair = "0"
    var table = "0"
    var pillow = "0"
    var window = "0"
    var tvinroom = "0"
    var wardrobe = "0"
    var hot_water = "0"
    var seat_toilet = "0"
    var squat_toilet = "0"
    var inside_bathroom = "1"
    var outside_bathroom = "0"
    var air_conditioning = "0"
    var electricity = "0"

    //general facilities
    var iron = "0"
    var wifi = "0"
    var kitchen = "0"
    var loundry = "0"
    var mushola = "0"
    var dispenser = "0"
    var car_parking = "0"
    var motorcycle_parking = "0"
    var bicycle_parking = "0"
    var living_room = "0"
    var tvinlivingroom = "0"
    var washing_machine = "0"
    var refrigerator = "0"

    ///other
    var mirror = "0"
    var sink = "0"
    var shower = "0"
    var bathub = "0"
    var ventilation = "0"

    //rules
    var pet = "0"
    var couple = "0"
    var person = "0"
    var check_in = "0"
    var check_out = "0"
    var children = "0"
    var add_electricity = "0"
    var smoking_in_room = "1"
    var special_student = "0"
    var special_employees = "0"
    var twf_hours_access = "0"

    //address
    var street = ""
    var village = ""
    var district = ""
    var city = ""
    var province = ""
    var postal_code = ""

    //price
    var cost_day = "0"
    var cost_week = "0"
    var cost_month = "0"

    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.isEnabled = false


        binding.btnBack.setOnClickListener {
            finish()
        }

        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
            }
        }

        var position = 0
        binding.stepView.done(false)
        binding.btnNext.setOnClickListener {
            when (position) {
                0 -> {
                    binding.btnNext.isEnabled = false
                    title = binding.layoutData.etName.editText!!.text.toString()
                    description = binding.layoutData.etDesc.editText!!.text.toString()
                    binding.layoutAddress.root.visibility = View.VISIBLE
                    binding.layoutData.root.visibility = View.GONE
                    position = 1
                    binding.stepView.done(false)
                    binding.stepView.go(position, true)
                }
                1 -> {
                    binding.btnNext.isEnabled = false
                    street = binding.layoutAddress.etStreet.editText!!.text.toString()
                    village = binding.layoutAddress.etVillage.editText!!.text.toString()
                    district = binding.layoutAddress.etDistrict.editText!!.text.toString()
                    city = binding.layoutAddress.etCity.editText!!.text.toString()
                    province = binding.layoutAddress.etProvince.editText!!.text.toString()
                    postal_code = binding.layoutAddress.etPostalCode.editText!!.text.toString()
                    binding.layoutAddress.root.visibility = View.GONE
                    binding.layoutPhoto.root.visibility = View.VISIBLE
                    position = 2
                    binding.stepView.done(false)
                    binding.stepView.go(position, true)
                }
                2 -> {
                    binding.layoutFacility.root.visibility = View.VISIBLE
                    binding.layoutPhoto.root.visibility = View.GONE
                    binding.btnNext.isEnabled = false
                    val file = reduceFileImage(getFile as File)
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "photo",
                        file.name,
                        requestImageFile
                    )
                    position = 3
                    binding.stepView.done(false)
                    binding.stepView.go(position, true)
                    binding.btnNext.isEnabled = true
                }
                3 -> {
                    binding.btnNext.isEnabled = false
                    wide =
                        "${binding.layoutRoom.etWidth.editText!!.text.toString()}x${binding.layoutRoom.etHeight.editText!!.text.toString()}"
                    stock = binding.layoutRoom.etStock.editText!!.text.toString()
                    binding.layoutFacility.root.visibility = View.GONE
                    binding.layoutRoom.root.visibility = View.VISIBLE
                    position = 4
                    binding.stepView.done(false)
                    binding.stepView.go(position, true)
                }
                4 -> {
                    binding.btnNext.isEnabled = false
                    cost_day = binding.layoutPrice.etDailyPrice.editText!!.text.toString()
                    cost_week = binding.layoutPrice.etWeekPrice.editText!!.text.toString()
                    cost_month = binding.layoutPrice.etMonthPrice.editText!!.text.toString()
                    binding.layoutPrice.root.visibility = View.VISIBLE
                    binding.layoutRoom.root.visibility = View.GONE
                    position = 5
                    binding.stepView.done(false)
                    binding.stepView.go(position, true)
                }
                else -> {
                    onSubmit()
                    position = 0
                    binding.stepView.done(true)
                }
            }
        }
        checkData()
        checkAddress()
        checkPhoto()
        checkFacilities()
        checkRoom()
        checkPrice()


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (position) {
                    0 -> {
                        finish()
                    }
                    1 -> {
                        binding.btnNext.isEnabled = true
                        binding.layoutAddress.root.visibility = View.GONE
                        binding.layoutData.root.visibility = View.VISIBLE
                        position = 0
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                    }
                    2 -> {
                        binding.btnNext.isEnabled = true
                        binding.layoutAddress.root.visibility = View.VISIBLE
                        binding.layoutPhoto.root.visibility = View.GONE
                        position = 1
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                    }
                    3 -> {
                        binding.btnNext.isEnabled = true
                        binding.layoutFacility.root.visibility = View.GONE
                        binding.layoutPhoto.root.visibility = View.VISIBLE
                        position = 2
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                        binding.btnNext.isEnabled = true
                    }
                    4 -> {
                        binding.btnNext.isEnabled = true
                        binding.layoutFacility.root.visibility = View.VISIBLE
                        binding.layoutRoom.root.visibility = View.GONE
                        position = 3
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                    }
                    else -> {
                        binding.btnNext.isEnabled = true
                        binding.layoutPrice.root.visibility = View.GONE
                        binding.layoutRoom.root.visibility = View.VISIBLE
                        position = 4
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                    }
                }

            }
        })
    }

    

    private fun onSubmit() {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("title",
                binding.layoutData.etName.editText!!.text.toString().lowercase())
            .addFormDataPart("type", type)
            .addFormDataPart("stock", binding.layoutRoom.etStock.editText!!.text.toString()
                .lowercase())
            .addFormDataPart("description", binding.layoutData.etDesc.editText!!.text.toString()
                .lowercase())
            .addFormDataPart("wide", "${
                binding.layoutRoom.etHeight.editText!!.text.toString()
                    .lowercase()
            }x${
                binding.layoutRoom.etWidth.editText!!.text.toString()
                    .lowercase()
            }")
            .addFormDataPart("bed", bed)
            .addFormDataPart("fan", fan)
            .addFormDataPart("chair", chair)
            .addFormDataPart("table", table)
            .addFormDataPart("pillow", pillow)
            .addFormDataPart("window", window)
            .addFormDataPart("tvinroom", tvinroom)
            .addFormDataPart("wardrobe", wardrobe)
            .addFormDataPart("hot_water", hot_water)
            .addFormDataPart("seat_toilet", seat_toilet)
            .addFormDataPart("squat_toilet", squat_toilet)
            .addFormDataPart("inside_bathroom", inside_bathroom)
            .addFormDataPart("outside_bathroom", outside_bathroom)
            .addFormDataPart("air_conditioning", air_conditioning)
            .addFormDataPart("electricity", electricity)
            .addFormDataPart("iron", iron)
            .addFormDataPart("wifi", wifi)
            .addFormDataPart("kitchen", kitchen)
            .addFormDataPart("loundry", loundry)
            .addFormDataPart("mushola", mushola)
            .addFormDataPart("dispenser", dispenser)
            .addFormDataPart("car_parking", car_parking)
            .addFormDataPart("motorcycle_parking", motorcycle_parking)
            .addFormDataPart("bicycle_parking", bicycle_parking)
            .addFormDataPart("living_room", living_room)
            .addFormDataPart("tvinlivingroom", tvinlivingroom)
            .addFormDataPart("washing_machine", washing_machine)
            .addFormDataPart("refrigerator", refrigerator)
            .addFormDataPart("mirror", mirror)
            .addFormDataPart("sink", sink)
            .addFormDataPart("shower", shower)
            .addFormDataPart("bathub", bathub)
            .addFormDataPart("ventilation", ventilation)
            .addFormDataPart("pet", pet)
            .addFormDataPart("couple", couple)
            .addFormDataPart("person", person)
            .addFormDataPart("check_in", check_in)
            .addFormDataPart("check_out", check_out)
            .addFormDataPart("children", children)
            .addFormDataPart("add_electricity", add_electricity)
            .addFormDataPart("smoking_in_room", smoking_in_room)
            .addFormDataPart("special_student", special_student)
            .addFormDataPart("special_employee", special_employees)
            .addFormDataPart("twf_hours_access", twf_hours_access)
            .addFormDataPart("street",
                binding.layoutAddress.etStreet.editText!!.text.toString()
                    .lowercase())
            .addFormDataPart("village",
                binding.layoutAddress.etVillage.editText!!.text.toString()
                    .lowercase())
            .addFormDataPart("district",
                binding.layoutAddress.etDistrict.editText!!.text.toString()
                    .lowercase())
            .addFormDataPart("city",
                binding.layoutAddress.etCity.editText!!.text.toString()
                    .lowercase())
            .addFormDataPart("province",
                binding.layoutAddress.etProvince.editText!!.text.toString()
                    .lowercase())
            .addFormDataPart("postal_code",
                binding.layoutAddress.etPostalCode.editText!!.text.toString()
                    .lowercase())
            .addFormDataPart("cost_day",
                binding.layoutPrice.etDailyPrice.editText!!.text.toString()
                    .lowercase())
            .addFormDataPart("cost_week",
                binding.layoutPrice.etWeekPrice.editText!!.text.toString()
                    .lowercase())
            .addFormDataPart("cost_month",
                binding.layoutPrice.etMonthPrice.editText!!.text.toString()
                    .lowercase())

        if (getFile?.exists() == true) {
            val file = reduceFileImage(getFile as File)
            builder.addFormDataPart("imageUrl",
                file.name,
                reduceFileImage(file).asRequestBody("image/jpeg".toMediaTypeOrNull()))
        }
        if (getFile2?.exists() == true) {
            val file = reduceFileImage(getFile2 as File)
            builder.addFormDataPart("imageUrl",
                file.name,
                reduceFileImage(file).asRequestBody("image/jpeg".toMediaTypeOrNull()))
        }
        if (getFile3?.exists() == true) {
            val file = reduceFileImage(getFile3 as File)
            builder.addFormDataPart("imageUrl",
                file.name,
                reduceFileImage(file).asRequestBody("image/jpeg".toMediaTypeOrNull()))
        }
        if (getFile4?.exists() == true) {
            val file = reduceFileImage(getFile4 as File)
            builder.addFormDataPart("imageUrl",
                file.name,
                reduceFileImage(file).asRequestBody("image/jpeg".toMediaTypeOrNull()))
        }

        val requestBody = builder.build()

        roomViewModel.addRoom(
            accessToken,
            requestBody
        ).observe(this@AddRoomActivity) { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading(this)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    val binding2: SentDialog2Binding =
                        SentDialog2Binding.inflate(LayoutInflater.from(this))
                    val builder2 = AlertDialog.Builder(this)
                    builder2.setView(binding2.root)
                    binding2.tvTittleSent.text = "Selamat Data Kost Telah Ditambahkan"
                    binding2.tvSent.text = "Kost Anda siap dipasarkan. Lihat statusnya di properti saya"
                    binding2.btnCheck.text = "Lihat Status"

                    builder2.setCancelable(false)
                    binding2.btnClose.setOnClickListener {
                        isdialoggg.dismiss()
                        val intent = Intent(this, HomePenyewaActivity::class.java)
                        finishAffinity()
                        startActivity(intent)
                    }
                    val displayMetrics = DisplayMetrics()
                    @Suppress("DEPRECATION")
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    val width = displayMetrics.widthPixels
                    isdialoggg = builder2.create()
                    isdialoggg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    isdialoggg.show()
                    val window: Window = isdialoggg.window!!
                    val wlp: WindowManager.LayoutParams = window.attributes
                    wlp.width = (width * 0.8).toInt()
                    window.attributes = wlp
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun checkFacilities() {
        binding.layoutFacility.cbBed.setOnCheckedChangeListener { _, b ->
            bed = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbFan.setOnCheckedChangeListener { _, b ->
            fan = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbChair.setOnCheckedChangeListener { _, b ->
            chair = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbDesk.setOnCheckedChangeListener { _, b ->
            table = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbPillow.setOnCheckedChangeListener { _, b ->
            pillow = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbWindow.setOnCheckedChangeListener { _, b ->
            window = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbTvInRoom.setOnCheckedChangeListener { _, b ->
            tvinroom = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbWardrobe.setOnCheckedChangeListener { _, b ->
            wardrobe = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbHotWater.setOnCheckedChangeListener { _, b ->
            hot_water = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbSitToilet.setOnCheckedChangeListener { _, b ->
            seat_toilet = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbSquatToilet.setOnCheckedChangeListener { _, b ->
            squat_toilet = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbBathInRoom.setOnCheckedChangeListener { _, b ->
            if (b) {
                inside_bathroom = "1"
                outside_bathroom = "0"
            } else {
                inside_bathroom = "0"
                outside_bathroom = "1"
            }
        }
        binding.layoutFacility.cbAc.setOnCheckedChangeListener { _, b ->
            air_conditioning = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbElecticity.setOnCheckedChangeListener { _, b ->
            electricity = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbIron.setOnCheckedChangeListener { _, b ->
            iron = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbWifi.setOnCheckedChangeListener { _, b ->
            iron = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbKitchen.setOnCheckedChangeListener { _, b ->
            kitchen = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbLaundry.setOnCheckedChangeListener { _, b ->
            loundry = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbMushola.setOnCheckedChangeListener { _, b ->
            mushola = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbDispenser.setOnCheckedChangeListener { _, b ->
            dispenser = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbParkingCar.setOnCheckedChangeListener { _, b ->
            car_parking = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbParkingMotor.setOnCheckedChangeListener { _, b ->
            motorcycle_parking = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbParkingBicycle.setOnCheckedChangeListener { _, b ->
            bicycle_parking = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbLivingRoom.setOnCheckedChangeListener { _, b ->
            living_room = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbTvInLivingRoom.setOnCheckedChangeListener { _, b ->
            tvinlivingroom = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbWashingMachine.setOnCheckedChangeListener { _, b ->
            washing_machine = if (b) {
                "1"
            } else {
                "0"
            }
        }
        binding.layoutFacility.cbRefrigerator.setOnCheckedChangeListener { _, b ->
            refrigerator = if (b) {
                "1"
            } else {
                "0"
            }
        }
    }


    private fun checkPrice() {
        binding.layoutPrice.etMonthPrice.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutPrice.etMonthPrice.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutPrice.etMonthPrice.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutPrice.etMonthPrice.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })

        binding.layoutPrice.etWeekPrice.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }

        binding.layoutPrice.etDailyPrice.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }

        binding.layoutPrice.cbPrice.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.layoutPrice.etWeekPrice.editText!!.isEnabled = true
                binding.layoutPrice.etDailyPrice.editText!!.isEnabled = true
            } else {
                binding.layoutPrice.etWeekPrice.editText!!.isEnabled = false
                binding.layoutPrice.etDailyPrice.editText!!.isEnabled = false
            }
        }
    }


    private fun checkRoom() {
        binding.layoutRoom.etWidth.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutRoom.etWidth.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutRoom.etWidth.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutRoom.etWidth.editText!!.text.isNotEmpty()
                                && binding.layoutRoom.etHeight.editText!!.text.isNotEmpty()
                                && binding.layoutRoom.etStock.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })
        binding.layoutRoom.etHeight.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutRoom.etHeight.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutRoom.etHeight.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutRoom.etWidth.editText!!.text.isNotEmpty()
                                && binding.layoutRoom.etHeight.editText!!.text.isNotEmpty()
                                && binding.layoutRoom.etStock.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })
        binding.layoutRoom.etStock.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutRoom.etStock.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutRoom.etStock.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutRoom.etWidth.editText!!.text.isNotEmpty()
                                && binding.layoutRoom.etHeight.editText!!.text.isNotEmpty()
                                && binding.layoutRoom.etStock.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })
    }


    private fun checkPhoto() {
        binding.layoutPhoto.cvImageDefault.setOnClickListener {
            setupDialog(1)
        }
        binding.layoutPhoto.cvImageDefault2.setOnClickListener {
            setupDialog(2)
        }
        binding.layoutPhoto.cvImageDefault3.setOnClickListener {
            setupDialog(3)
        }
        binding.layoutPhoto.cvImageDefault4.setOnClickListener {
            setupDialog(4)
        }
    }

    private fun setupDialog(i: Int) {
        val mediaBinding: CardChooseImageBinding =
            CardChooseImageBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(mediaBinding.root)
        builder.setCancelable(true)
        isdialogg = builder.create()
        isdialogg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isdialogg.show()
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val window: Window = isdialogg.window!!
        val wlp: WindowManager.LayoutParams = window.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = width
        window.attributes = wlp
        mediaBinding.btnCamera.setOnClickListener {
            isdialogg.dismiss()
            openCamera(i)
        }
        mediaBinding.btnGallery.setOnClickListener {
            isdialogg.dismiss()
            openGallery(i)
        }
        binding.btnNext.isEnabled = true
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun checkAddress() {
        binding.layoutAddress.etStreet.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }

        binding.layoutAddress.etStreet.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutAddress.etStreet.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutAddress.etStreet.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutAddress.etStreet.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etVillage.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etDistrict.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etCity.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etProvince.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etPostalCode.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })


        binding.layoutAddress.etVillage.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutAddress.etVillage.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutAddress.etVillage.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutAddress.etStreet.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etVillage.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etDistrict.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etCity.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etProvince.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etPostalCode.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })


        binding.layoutAddress.etDistrict.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutAddress.etDistrict.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutAddress.etDistrict.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutAddress.etStreet.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etVillage.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etDistrict.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etCity.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etProvince.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etPostalCode.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })


        binding.layoutAddress.etCity.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutAddress.etCity.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutAddress.etCity.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutAddress.etStreet.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etVillage.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etDistrict.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etCity.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etProvince.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etPostalCode.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })



        binding.layoutAddress.etProvince.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutAddress.etProvince.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutAddress.etProvince.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutAddress.etStreet.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etVillage.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etDistrict.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etCity.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etProvince.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etPostalCode.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })

        binding.layoutAddress.etPostalCode.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }
        binding.layoutAddress.etPostalCode.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutAddress.etPostalCode.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutAddress.etStreet.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etVillage.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etDistrict.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etCity.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etProvince.editText!!.text.isNotEmpty()
                                && binding.layoutAddress.etPostalCode.editText!!.text.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun checkData() {

        binding.layoutData.cbPet.setOnCheckedChangeListener { _, b ->
            pet = if (b) {
                "1"
            } else {
                "0"
            }
        }

        binding.layoutData.cbCouple.setOnCheckedChangeListener { _, b ->
            couple = if (b) {
                "1"
            } else {
                "0"
            }
        }

        binding.layoutData.cbPerson.setOnCheckedChangeListener { _, b ->
            person = if (b) {
                "2"
            } else {
                "0"
            }
        }

        binding.layoutData.cbChildren.setOnCheckedChangeListener { _, b ->
            children = if (b) {
                "1"
            } else {
                "0"
            }
        }

        binding.layoutData.cbAddElectricity.setOnCheckedChangeListener { _, b ->
            add_electricity = if (b) {
                "1"
            } else {
                "0"
            }
        }

        binding.layoutData.cbSmokingInRoom.setOnCheckedChangeListener { _, b ->
            smoking_in_room = if (b) {
                "0"
            } else {
                "1"
            }
        }

        binding.layoutData.cbSpecialStudent.setOnCheckedChangeListener { _, b ->
            special_student = if (b) {
                "1"
            } else {
                "0"
            }
        }

        binding.layoutData.cbSpecialEmployee.setOnCheckedChangeListener { _, b ->
            special_employees = if (b) {
                "1"
            } else {
                "0"
            }
        }

        binding.layoutData.cbCheckIn.setOnCheckedChangeListener { _, b ->
            check_in = if (b) {
                "1"
            } else {
                "0"
            }
        }

        binding.layoutData.cbCheckOut.setOnCheckedChangeListener { _, b ->
            check_out = if (b) {
                "1"
            } else {
                "0"
            }
        }

        binding.layoutData.etDesc.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }

        binding.layoutData.etDesc.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }

        binding.layoutData.etName.editText!!.setOnFocusChangeListener { _, b ->
            if (b) {
                binding.cvNext.visibility = View.GONE
            } else {
                binding.cvNext.visibility = View.VISIBLE
            }
        }


        binding.layoutData.cvMan.strokeColor = Color.parseColor("#C7C6CA")
        binding.layoutData.cvWoman.strokeColor = Color.parseColor("#C7C6CA")
        binding.layoutData.cvManWoman.strokeColor = Color.parseColor("#C7C6CA")
        binding.layoutData.cvMan.setOnClickListener {
            type = "putra"
            binding.layoutData.cvMan.strokeWidth = 5
            binding.layoutData.cvMan.strokeColor = resources.getColor(R.color.primary)
            binding.layoutData.cvWoman.strokeWidth = 3
            binding.layoutData.cvWoman.strokeColor = Color.parseColor("#C7C6CA")
            binding.layoutData.cvManWoman.strokeWidth = 3
            binding.layoutData.cvManWoman.strokeColor = Color.parseColor("#C7C6CA")
            binding.btnNext.isEnabled =
                binding.layoutData.etName.editText!!.text.isNotEmpty() && binding.layoutData.etDesc.editText!!.text.isNotEmpty() && type.isNotEmpty()
        }
        binding.layoutData.cvWoman.setOnClickListener {
            type = "putri"
            binding.layoutData.cvMan.strokeWidth = 3
            binding.layoutData.cvMan.strokeColor = Color.parseColor("#C7C6CA")
            binding.layoutData.cvWoman.strokeWidth = 5
            binding.layoutData.cvWoman.strokeColor = resources.getColor(R.color.primary)
            binding.layoutData.cvManWoman.strokeWidth = 3
            binding.layoutData.cvManWoman.strokeColor = Color.parseColor("#C7C6CA")
            binding.btnNext.isEnabled =
                binding.layoutData.etName.editText!!.text.isNotEmpty() && binding.layoutData.etDesc.editText!!.text.isNotEmpty() && type.isNotEmpty()
        }
        binding.layoutData.cvManWoman.setOnClickListener {
            type = "campur"
            binding.layoutData.cvMan.strokeWidth = 3
            binding.layoutData.cvMan.strokeColor = Color.parseColor("#C7C6CA")
            binding.layoutData.cvWoman.strokeWidth = 3
            binding.layoutData.cvWoman.strokeColor = Color.parseColor("#C7C6CA")
            binding.layoutData.cvManWoman.strokeWidth = 5
            binding.layoutData.cvManWoman.strokeColor = resources.getColor(R.color.primary)
            binding.btnNext.isEnabled =
                binding.layoutData.etName.editText!!.text.isNotEmpty() && binding.layoutData.etDesc.editText!!.text.isNotEmpty() && type.isNotEmpty()
        }


        binding.layoutData.etName.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutData.etName.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutData.etName.editText!!.text.isNotEmpty() && binding.layoutData.etDesc.editText!!.text.isNotEmpty() && type.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })

        binding.layoutData.etDesc.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.layoutData.etDesc.editText!!.text.isNotEmpty()) {
                    binding.btnNext.isEnabled =
                        binding.layoutData.etName.editText!!.text.isNotEmpty() && binding.layoutData.etDesc.editText!!.text.isNotEmpty() && type.isNotEmpty()
                } else {
                    binding.btnNext.isEnabled = false
                }
            }
        })
    }


    private fun openGallery(i: Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        when (i) {
            1 -> {
                launcherIntentGallery.launch(chooser)
            }
            2 -> {
                launcherIntentGallery2.launch(chooser)
            }
            3 -> {
                launcherIntentGallery3.launch(chooser)
            }
            4 -> {
                launcherIntentGallery4.launch(chooser)
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera(i: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddRoomActivity,
                "com.binar.kos.view.ui",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            when (i) {
                1 -> {
                    launcherIntentCamera.launch(intent)
                }
                2 -> {
                    launcherIntentCamera2.launch(intent)
                }
                3 -> {
                    launcherIntentCamera3.launch(intent)
                }
                4 -> {
                    launcherIntentCamera4.launch(intent)
                }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            binding.layoutPhoto.imageContainer.visibility = View.GONE
            binding.layoutPhoto.imagePhoto.visibility = View.VISIBLE
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            Glide.with(this)
                .load(result)
                .into(binding.layoutPhoto.imagePhoto)
            binding.layoutPhoto.cvImage2.visibility = View.VISIBLE

        } else {
            if (getFile?.exists() != true) {
                binding.layoutPhoto.imageContainer.visibility = View.VISIBLE
                binding.layoutPhoto.imagePhoto.visibility = View.GONE
            }
        }
    }

    private val launcherIntentCamera2 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            binding.layoutPhoto.imageContainer2.visibility = View.GONE
            binding.layoutPhoto.imagePhoto2.visibility = View.VISIBLE
            val myFile = File(currentPhotoPath)
            getFile2 = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            Glide.with(this)
                .load(result)
                .into(binding.layoutPhoto.imagePhoto2)
            binding.layoutPhoto.cvImage3.visibility = View.VISIBLE

        } else {
            if (getFile2?.exists() != true) {
                binding.layoutPhoto.imageContainer2.visibility = View.VISIBLE
                binding.layoutPhoto.imagePhoto2.visibility = View.GONE
            }
        }
    }
    private val launcherIntentCamera3 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            binding.layoutPhoto.imageContainer3.visibility = View.GONE
            binding.layoutPhoto.imagePhoto3.visibility = View.VISIBLE
            val myFile = File(currentPhotoPath)
            getFile3 = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            Glide.with(this)
                .load(result)
                .into(binding.layoutPhoto.imagePhoto3)
            binding.layoutPhoto.cvImage4.visibility = View.VISIBLE

        } else {
            if (getFile3?.exists() != true) {
                binding.layoutPhoto.imageContainer3.visibility = View.VISIBLE
                binding.layoutPhoto.imagePhoto3.visibility = View.GONE
            }
        }
    }
    private val launcherIntentCamera4 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            binding.layoutPhoto.imageContainer4.visibility = View.GONE
            binding.layoutPhoto.imagePhoto4.visibility = View.VISIBLE
            val myFile = File(currentPhotoPath)
            getFile4 = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            Glide.with(this)
                .load(result)
                .into(binding.layoutPhoto.imagePhoto4)
        } else {
            if (getFile4?.exists() != true) {
                binding.layoutPhoto.imageContainer3.visibility = View.VISIBLE
                binding.layoutPhoto.imagePhoto4.visibility = View.GONE
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.layoutPhoto.imageContainer.visibility = View.GONE
            binding.layoutPhoto.imagePhoto.visibility = View.VISIBLE
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddRoomActivity)
            getFile = myFile
            Glide.with(this)
                .load(myFile)
                .into(binding.layoutPhoto.imagePhoto)
            binding.layoutPhoto.cvImage2.visibility = View.VISIBLE

        } else {
            if (getFile?.exists() != true) {
                binding.layoutPhoto.imageContainer.visibility = View.VISIBLE
                binding.layoutPhoto.imagePhoto.visibility = View.GONE
            }
        }
    }

    private val launcherIntentGallery2 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.layoutPhoto.imageContainer2.visibility = View.GONE
            binding.layoutPhoto.imagePhoto2.visibility = View.VISIBLE
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddRoomActivity)
            getFile2 = myFile
            Glide.with(this)
                .load(myFile)
                .into(binding.layoutPhoto.imagePhoto2)
            binding.layoutPhoto.cvImage3.visibility = View.VISIBLE

        } else {
            if (getFile2?.exists() != true) {
                binding.layoutPhoto.imageContainer2.visibility = View.VISIBLE
                binding.layoutPhoto.imagePhoto2.visibility = View.GONE
            }
        }
    }

    private val launcherIntentGallery3 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.layoutPhoto.imageContainer3.visibility = View.GONE
            binding.layoutPhoto.imagePhoto3.visibility = View.VISIBLE
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddRoomActivity)
            getFile3 = myFile
            Glide.with(this)
                .load(myFile)
                .into(binding.layoutPhoto.imagePhoto3)
            binding.layoutPhoto.cvImage4.visibility = View.VISIBLE

        } else {
            if (getFile3?.exists() != true) {
                binding.layoutPhoto.imageContainer3.visibility = View.VISIBLE
                binding.layoutPhoto.imagePhoto3.visibility = View.GONE
            }
        }
    }

    private val launcherIntentGallery4 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.layoutPhoto.imageContainer4.visibility = View.GONE
            binding.layoutPhoto.imagePhoto4.visibility = View.VISIBLE
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddRoomActivity)
            getFile4 = myFile
            Glide.with(this)
                .load(myFile)
                .into(binding.layoutPhoto.imagePhoto4)
        } else {
            if (getFile4?.exists() != true) {
                binding.layoutPhoto.imageContainer4.visibility = View.VISIBLE
                binding.layoutPhoto.imagePhoto4.visibility = View.GONE
            }
        }
    }

    private var getFile: File? = null
    private var getFile2: File? = null
    private var getFile3: File? = null
    private var getFile4: File? = null

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
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

}