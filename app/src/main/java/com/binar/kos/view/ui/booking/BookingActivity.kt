package com.binar.kos.view.ui.booking

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import com.binar.kos.R
import com.binar.kos.data.remote.response.userdataResponse.UserdataResponse
import com.binar.kos.databinding.ActivityBookingBinding
import com.binar.kos.databinding.BottomsheetBookerBinding
import com.binar.kos.databinding.BottomsheetFacilityBinding
import com.binar.kos.databinding.BottomsheetImageBinding
import com.binar.kos.databinding.BottomsheetTimeBookBinding
import com.binar.kos.utils.*
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.room.RoomActivity
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.view.ui.transaction.TransactionActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.LogoutViewModel
import com.binar.kos.viewmodel.RoomViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var bindingBottomsheetTimeBook: BottomsheetTimeBookBinding
    private lateinit var bindingBottomsheetBooker: BottomsheetBookerBinding
    private var type_cost = "bulan"
    private var bookerCount = 1
    private var timeCount = 1
    private var roomCost = 0
    private var feeCost: Double = 0.0
    private var totalCost = 0
    private var roomCostMonthly = ""
    private var roomCostWeekly = ""
    private var roomCostDaily = ""
    private var accessToken = ""
    private var roomId = 0
    private var userId = 0
    private var rentalDate = ""

    private val dataStore: DatastoreViewModel by viewModel()
    private val logoutViewModel: LogoutViewModel by viewModel()
    private val roomViewModel: RoomViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindingBottomsheetBooker = BottomsheetBookerBinding.inflate(LayoutInflater.from(this))
        bindingBottomsheetTimeBook = BottomsheetTimeBookBinding.inflate(LayoutInflater.from(this))
        binding.cvPromo.visibility = View.GONE

        setupData()
        setupBottomSheet()
        radioTime()
        setupDate()
        setupCounter()
        getUserdata()

        bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = true

        binding.btnSewa.setOnClickListener {
            onSubmit()
        }
    }


    private fun onSubmit() {
        var tokenn = ""
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                tokenn = token
            }
        }
        roomViewModel.bookingPost(
            tokenn,
            roomId,
            bookerCount.toString(),
            roomCost.toString(),
            type_cost,
            totalCost.toString(),
            userId.toString()
        ).observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading(this)
                }
                Status.SUCCESS -> {
                    roomViewModel.bookingPut(
                        tokenn,
                        result.data!!.id!!,
                        binding.cbCouple.isChecked.toString(),
                        binding.cbChildren.isChecked.toString(),
                        "true",
                        binding.etNotes.editText?.text.toString(),
                        rentalDate,
                        roomCost.toString(),
                        totalCost.toString()
                    ).observe(this) { resultPut ->
                        when (resultPut.status) {
                            Status.LOADING -> {
                            }
                            Status.SUCCESS -> {
                                hideLoading()
                                val intent = Intent(this, TransactionActivity::class.java)
                                intent.putExtra(BOOKING_ID, resultPut.data?.id)
                                intent.putExtra(BOOKING_ROOM_COST, roomCost)
                                intent.putExtra(BOOKING_FEE_COST, feeCost.toInt())
                                intent.putExtra(BOOKING_TOTAL_COST, totalCost)
                                intent.putExtra(BOOKING_ROOM_INFO, "Biaya Kost $timeCount/$type_cost")
                                finish()
                                startActivity(intent)
                            }
                            Status.ERROR -> {
                                hideLoading()
                                Toast.makeText(this, resultPut.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun checkButtonUserdata() {
        bindingBottomsheetBooker.btnSewa.isEnabled =
            bindingBottomsheetBooker.etNameBooker.isNotEmpty() &&
                    bindingBottomsheetBooker.etNumberBooker.isNotEmpty() &&
                    bindingBottomsheetBooker.etJobBooker.isNotEmpty()

        bindingBottomsheetBooker.etNameBooker.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (bindingBottomsheetBooker.etNameBooker.editText?.text!!.isEmpty()) {
                        bindingBottomsheetBooker.etNameBooker.error = "Nama tidak boleh kosong!"
                    }
                }
            }
        bindingBottomsheetBooker.etNumberBooker.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (bindingBottomsheetBooker.etNameBooker.editText?.text!!.isEmpty()) {
                        bindingBottomsheetBooker.etNameBooker.error =
                            "No telepon tidak boleh kosong!"
                    }
                }
            }
        bindingBottomsheetBooker.etJobBooker.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    if (bindingBottomsheetBooker.etJobBooker.editText?.text!!.isEmpty()) {
                        bindingBottomsheetBooker.etJobBooker.error =
                            "Pekerjaan tidak boleh kosong!"
                    }
                }
            }


        bindingBottomsheetBooker.etNameBooker.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                bindingBottomsheetBooker.btnSewa.isEnabled =
                    bindingBottomsheetBooker.etNameBooker.isNotEmpty() &&
                            bindingBottomsheetBooker.etNumberBooker.isNotEmpty() &&
                            bindingBottomsheetBooker.etJobBooker.isNotEmpty()
                binding.tvBookerName.text =
                    bindingBottomsheetBooker.etNameBooker.editText?.text.toString().toCapital()
            }
        })

        bindingBottomsheetBooker.etNumberBooker.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                bindingBottomsheetBooker.btnSewa.isEnabled =
                    bindingBottomsheetBooker.etNameBooker.isNotEmpty() &&
                            bindingBottomsheetBooker.etNumberBooker.isNotEmpty() &&
                            bindingBottomsheetBooker.etJobBooker.isNotEmpty()
                binding.tvBookerNumber.text =
                    bindingBottomsheetBooker.etNumberBooker.editText?.text.toString()
                        .toCapital()

            }
        })
        bindingBottomsheetBooker.etJobBooker.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                bindingBottomsheetBooker.btnSewa.isEnabled =
                    bindingBottomsheetBooker.etNameBooker.isNotEmpty() &&
                            bindingBottomsheetBooker.etNumberBooker.isNotEmpty() &&
                            bindingBottomsheetBooker.etJobBooker.isNotEmpty()
                binding.tvBookerJob.text =
                    bindingBottomsheetBooker.etJobBooker.editText?.text.toString().toCapital()
            }
        })

        bindingBottomsheetBooker.cbMan.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.tvBookerGender.text = "Laki-laki"
            } else {
                binding.tvBookerGender.text = "Perempuan"
            }
        }
        bindingBottomsheetBooker.cbWoman.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.tvBookerGender.text = "Perempuan"
            } else {
                binding.tvBookerGender.text = "Laki-laki"
            }
        }
    }


    private fun getUserdata() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
                logoutViewModel.getUsedata(token).observe(this@BookingActivity) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                            showLoading(this)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            setupUserdata(result.data!!)
                        }
                        Status.ERROR -> {
                            hideLoading()
                            Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUserdata(data: UserdataResponse) {
        userId = data.data!!.id!!
        binding.tvBookerName.text = data.data.namaLengkap.toString().toCapital()
        bindingBottomsheetBooker.etNameBooker.editText!!.setText(data.data.namaLengkap.toString()
            .toCapital())
        binding.tvBookerNumber.text = data.data.noTelepon.toString().toCapital()
        bindingBottomsheetBooker.etNumberBooker.editText!!.setText(data.data.noTelepon.toString()
            .toCapital())
        binding.tvBookerGender.text = data.data.gender.toString().toCapital()
        if (data.data.gender.toString() == "perempuan") {
            bindingBottomsheetBooker.cbWoman.isChecked = true
            bindingBottomsheetBooker.cbMan.isChecked = false
        } else {
            bindingBottomsheetBooker.cbWoman.isChecked = false
            bindingBottomsheetBooker.cbMan.isChecked = true
        }
        binding.tvBookerJob.text = data.data.profesi.toString().toCapital()
        bindingBottomsheetBooker.etJobBooker.editText!!.setText(data.data.profesi.toString()
            .toCapital())


        checkButtonUserdata()
    }


    private fun setupData() {
        roomId = intent.getIntExtra(RoomActivity.ROOM_ID, 0)
        val roomName = intent.getStringExtra(RoomActivity.ROOM_NAME)
        val roomImage = intent.getStringExtra(RoomActivity.ROOM_IMAGE)
        val roomType = intent.getStringExtra(RoomActivity.ROOM_TYPE)
        val roomLocation = intent.getStringExtra(RoomActivity.ROOM_LOCATION)
        roomCostMonthly = intent.getStringExtra(RoomActivity.ROOM_COST_MONTHLY).toString()
        roomCostWeekly = intent.getStringExtra(RoomActivity.ROOM_COST_WEEKLY).toString()
        roomCostDaily = intent.getStringExtra(RoomActivity.ROOM_COST_DAILY).toString()

        if (roomName!!.isNotEmpty()) {
            binding.tvTitleRoom.text = roomName.toCapital()
        }
        if (roomImage!!.isNotEmpty()) {
            val requestOptions = RequestOptions().placeholder(R.drawable.kos_dummy_image)
            Glide.with(this).load(roomImage.toString()).apply(requestOptions)
                .skipMemoryCache(true)
                .into(binding.ivRoom)
        }
        if (roomType!!.isNotEmpty()) {
            binding.tvType.text = roomType.toCapital()
        }
        if (roomLocation!!.isNotEmpty()) {
            binding.tvLocationRoom.text = roomLocation.toCapital()
        }
        if (roomCostMonthly.isNotEmpty()) {
            roomCost = roomCostMonthly.toInt() * timeCount
            binding.tvPriceTime.text = roomCostMonthly.toInt().toRp()
            bindingBottomsheetTimeBook.tvMonthlyCost.text = roomCostMonthly.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCostMonthly.toInt().toRp()
            feeCost = 0.02 * roomCostMonthly.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
        }
        if (roomCostWeekly.isNotEmpty()) {
            bindingBottomsheetTimeBook.tvWeeklyCost.text = roomCostWeekly.toInt().toRp()
        }
        if (roomCostDaily.isNotEmpty()) {
            bindingBottomsheetTimeBook.tvDailyCost.text = roomCostDaily.toInt().toRp()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setupCounter() {
        bookerCount = 1
        binding.tvCountBooker.text = bookerCount.toString()
        binding.ivAddCount.setOnClickListener {
            bookerCount += 1
            binding.tvCountBooker.text = bookerCount.toString()
        }
        binding.ivRemoveCount.setOnClickListener {
            if (bookerCount == 1) {
                binding.tvCountBooker.text = bookerCount.toString()
            } else {
                bookerCount -= 1
                binding.tvCountBooker.text = bookerCount.toString()
            }
        }

        binding.tvCountTime.text = "$timeCount $type_cost"
        binding.ivAddCountDuration.setOnClickListener {
            timeCount += 1
            binding.tvCountTime.text = "$timeCount $type_cost"
            roomCost = when (type_cost) {
                "hari" -> {
                    roomCostDaily.toInt() * timeCount
                }
                "minggu" -> {
                    roomCostWeekly.toInt() * timeCount
                }
                else -> {
                    roomCostMonthly.toInt() * timeCount
                }
            }
            binding.tvCostPaymentPrice.text = roomCost.toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
        }
        binding.ivRemoveCountDuration.setOnClickListener {
            if (timeCount == 1) {
                roomCost = when (type_cost) {
                    "hari" -> {
                        roomCostDaily.toInt() * timeCount
                    }
                    "minggu" -> {
                        roomCostWeekly.toInt() * timeCount
                    }
                    else -> {
                        roomCostMonthly.toInt() * timeCount
                    }
                }
                binding.tvCountTime.text = "$timeCount $type_cost"
                binding.tvCostPaymentPrice.text = roomCost.toRp()
                feeCost = 0.02 * roomCost.toDouble()
                binding.tvCostFeePrice.text = feeCost.toInt().toRp()
                totalCost = roomCost + feeCost.toInt()
                binding.tvCostTotalPrice.text = totalCost.toRp()
            } else {
                timeCount -= 1
                roomCost = when (type_cost) {
                    "hari" -> {
                        roomCostDaily.toInt() * timeCount
                    }
                    "minggu" -> {
                        roomCostWeekly.toInt() * timeCount
                    }
                    else -> {
                        roomCostMonthly.toInt() * timeCount
                    }
                }
                binding.tvCountTime.text = "$timeCount $type_cost"
                binding.tvCostPaymentPrice.text = roomCost.toRp()
                feeCost = 0.02 * roomCost.toDouble()
                binding.tvCostFeePrice.text = feeCost.toInt().toRp()
                totalCost = roomCost + feeCost.toInt()
                binding.tvCostTotalPrice.text = totalCost.toRp()
            }
        }
    }


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setupDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val formatDay = SimpleDateFormat("EEEE")
        val formatMonth = SimpleDateFormat("MMMM")
        val datePickerDialog = DatePickerDialog(
            this,
            { _, mYear, monthOfYear, dayOfMonth ->
                val date = Date(mYear, monthOfYear, dayOfMonth - 1)
                val stringDay = formatDay.format(date)
                val stringMonth = formatMonth.format(date)
                val formatMonth2 = SimpleDateFormat("MM")
                val stringMonth2 = formatMonth2.format(date)
                rentalDate = "$mYear-$stringMonth2-$dayOfMonth"
                binding.tvStartTime.text = "${stringDay}, $dayOfMonth $stringMonth $mYear"
            },
            year,
            month,
            day
        )
        val dateNow = System.currentTimeMillis() - 1000
        val formatt = SimpleDateFormat("EEEE, dd MMMM yyyy")
        val date = Date(dateNow)
        val dateFormatted = formatt.format(date)
        binding.tvStartTime.text = dateFormatted
        binding.btnEditStartTime.setOnClickListener {
            datePickerDialog.datePicker.minDate = dateNow
            datePickerDialog.show()
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun setupBottomSheet() {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val height = displayMetrics.heightPixels


        val bottomSheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val bottomSheetDialog2 = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)

        bottomSheetDialog.setContentView(bindingBottomsheetBooker.root)
        bottomSheetDialog2.setContentView(bindingBottomsheetTimeBook.root)

        val bottomSheetBehavior: BottomSheetBehavior<View> =
            BottomSheetBehavior.from(bindingBottomsheetBooker.root.parent as View)
        val bottomSheetBehavior2: BottomSheetBehavior<View> =
            BottomSheetBehavior.from(bindingBottomsheetTimeBook.root.parent as View)

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
                        bindingBottomsheetBooker.root.background =
                            getDrawable(R.drawable.rounded_bottom_sheet)
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bindingBottomsheetBooker.root.background =
                            getDrawable(R.color.background)
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bindingBottomsheetBooker.root.background =
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

        bindingBottomsheetBooker.btnClose.setOnClickListener {
            bindingBottomsheetBooker.root.background =
                getDrawable(R.drawable.rounded_bottom_sheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheetDialog.dismiss()
        }

        bindingBottomsheetBooker.btnSewa.setOnClickListener {
            bindingBottomsheetBooker.root.background =
                getDrawable(R.drawable.rounded_bottom_sheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheetDialog.dismiss()
        }

        bindingBottomsheetTimeBook.btnClose.setOnClickListener {
            if(bindingBottomsheetTimeBook.cbDailyCost.isChecked){
                type_cost = "hari"
            }else if(bindingBottomsheetTimeBook.cbWeeklyCost.isChecked){
                type_cost = "minggu"
            }else if(bindingBottomsheetTimeBook.cbMonthlyCost.isChecked){
                type_cost = "bulan"
            }
            binding.tvCountTime.text = "$timeCount $type_cost"
            binding.tvCostPayment.text = "Biaya Kost $timeCount/$type_cost"
            roomCost = roomCostWeekly.toInt() * timeCount
            binding.tvPriceTime.text = roomCostWeekly.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCost.toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
            bottomSheetDialog2.dismiss()
        }

        bindingBottomsheetTimeBook.btnSave.setOnClickListener {
            if(bindingBottomsheetTimeBook.cbDailyCost.isChecked){
                type_cost = "hari"
            }else if(bindingBottomsheetTimeBook.cbWeeklyCost.isChecked){
                type_cost = "minggu"
            }else if(bindingBottomsheetTimeBook.cbMonthlyCost.isChecked){
                type_cost = "bulan"
            }
            binding.tvCountTime.text = "$timeCount $type_cost"
            binding.tvCostPayment.text = "Biaya Kost $timeCount/$type_cost"
            roomCost = roomCostWeekly.toInt() * timeCount
            binding.tvPriceTime.text = roomCostWeekly.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCost.toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
            bottomSheetDialog2.dismiss()
        }

        binding.btnEditTime.setOnClickListener {
            bottomSheetDialog2.show()
        }

        binding.ivEditBooker.setOnClickListener {
            bindingBottomsheetBooker.root.minimumHeight = height + 52
            bottomSheetBehavior.peekHeight = height + 52
            bottomSheetDialog.show()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    @SuppressLint("SetTextI18n")
    private fun radioTime() {
        bindingBottomsheetTimeBook.cvDailyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = true
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = false
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = false
            type_cost = "hari"
            binding.tvCountTime.text = "$timeCount $type_cost"
            binding.tvCostPayment.text = "Biaya Kost $timeCount/$type_cost"
            roomCost = roomCostDaily.toInt() * timeCount
            binding.tvPriceTime.text = roomCostDaily.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCost.toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
        }
        bindingBottomsheetTimeBook.cvWeeklyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = false
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = true
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = false
            type_cost = "minggu"
            binding.tvCountTime.text = "$timeCount $type_cost"
            binding.tvCostPayment.text = "Biaya Kost $timeCount/$type_cost"
            roomCost = roomCostWeekly.toInt() * timeCount
            binding.tvPriceTime.text = roomCostWeekly.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCost.toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
        }
        bindingBottomsheetTimeBook.cvMonthlyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = false
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = false
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = true
            type_cost = "bulan"
            binding.tvCountTime.text = "$timeCount $type_cost"
            binding.tvCostPayment.text = "Biaya Kost $timeCount/$type_cost"
            roomCost = roomCostMonthly.toInt() * timeCount
            binding.tvPriceTime.text = roomCostMonthly.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCostMonthly.toInt().toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
        }
        bindingBottomsheetTimeBook.cbDailyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = true
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = false
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = false
            type_cost = "hari"
            binding.tvCountTime.text = "$timeCount $type_cost"
            binding.tvCostPayment.text = "Biaya Kost $timeCount/$type_cost"
            roomCost = roomCostDaily.toInt() * timeCount
            binding.tvPriceTime.text = roomCostDaily.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCost.toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
        }
        bindingBottomsheetTimeBook.cbWeeklyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = false
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = true
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = false
            type_cost = "minggu"
            binding.tvCountTime.text = "$timeCount $type_cost"
            binding.tvCostPayment.text = "Biaya Kost $timeCount/$type_cost"
            roomCost = roomCostWeekly.toInt() * timeCount
            binding.tvPriceTime.text = roomCostWeekly.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCost.toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
        }
        bindingBottomsheetTimeBook.cbMonthlyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = false
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = false
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = true
            type_cost = "bulan"
            binding.tvCountTime.text = "$timeCount $type_cost"
            binding.tvCostPayment.text = "Biaya Kost $timeCount/$type_cost"
            roomCost = roomCostMonthly.toInt() * timeCount
            binding.tvPriceTime.text = roomCostMonthly.toInt().toRp()
            binding.tvCostPaymentPrice.text = roomCost.toRp()
            feeCost = 0.02 * roomCost.toDouble()
            binding.tvCostFeePrice.text = feeCost.toInt().toRp()
            totalCost = roomCost + feeCost.toInt()
            binding.tvCostTotalPrice.text = totalCost.toRp()
        }
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
        const val BOOKING_ID = "booking_id"
        const val BOOKING_ROOM_COST = "booking_room_cost"
        const val BOOKING_FEE_COST = "booking_fee_cost"
        const val BOOKING_TOTAL_COST = "booking_total_cost"
        const val BOOKING_ROOM_INFO = "booking_room_info"
    }

}