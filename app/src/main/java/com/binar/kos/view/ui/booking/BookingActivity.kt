package com.binar.kos.view.ui.booking

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import com.binar.kos.R
import com.binar.kos.databinding.ActivityBookingBinding
import com.binar.kos.databinding.BottomsheetBookerBinding
import com.binar.kos.databinding.BottomsheetFacilityBinding
import com.binar.kos.databinding.BottomsheetImageBinding
import com.binar.kos.databinding.BottomsheetTimeBookBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var bindingBottomsheetTimeBook: BottomsheetTimeBookBinding
    private lateinit var bindingBottomsheetBooker: BottomsheetBookerBinding
    private var type_cost = "bulan"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomSheet()
        radioTime()
        setupDate()
        setupCounter()

        bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = true

    }


    @SuppressLint("SetTextI18n")
    private fun setupCounter(){
        var bookerCount = 1
        binding.tvCountBooker.text = bookerCount.toString()
        binding.ivAddCount.setOnClickListener {
            bookerCount += 1
            binding.tvCountBooker.text = bookerCount.toString()
        }
        binding.ivRemoveCount.setOnClickListener {
            if(bookerCount==1){
                binding.tvCountBooker.text = bookerCount.toString()
            }else{
                bookerCount -= 1
                binding.tvCountBooker.text = bookerCount.toString()
            }
        }

        var timeCount = 1
        binding.tvCountTime.text = "$timeCount $type_cost"
        binding.ivAddCountDuration.setOnClickListener {
            timeCount += 1
            binding.tvCountTime.text = "$timeCount $type_cost"
        }
        binding.ivRemoveCountDuration.setOnClickListener {
            if(timeCount==1){
                binding.tvCountTime.text = "$timeCount $type_cost"
            }else{
                timeCount -= 1
                binding.tvCountTime.text = "$timeCount $type_cost"
            }
        }
    }



    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setupDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val formatDay = SimpleDateFormat("EEEE")
        val formatMonth = SimpleDateFormat("MMMM")
        val datePickerDialog = DatePickerDialog(
            this,
            { _, mYear, monthOfYear, dayOfMonth ->
                val date = Date(mYear, monthOfYear, dayOfMonth -1)
                val stringDay = formatDay.format(date)
                val stringMonth = formatMonth.format(date)
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


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupBottomSheet() {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val height = displayMetrics.heightPixels

        bindingBottomsheetBooker = BottomsheetBookerBinding.inflate(LayoutInflater.from(this))
        bindingBottomsheetTimeBook = BottomsheetTimeBookBinding.inflate(LayoutInflater.from(this))

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
                        bindingBottomsheetBooker.root.background = getDrawable(R.color.background)
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
            bottomSheetDialog2.dismiss()
        }

        bindingBottomsheetTimeBook.btnSave.setOnClickListener {
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

    private fun radioTime(){
        bindingBottomsheetTimeBook.cvDailyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = true
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = false
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = false
            type_cost ="hari"
        }
        bindingBottomsheetTimeBook.cvWeeklyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = false
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = true
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = false
            type_cost = "minggu"
        }
        bindingBottomsheetTimeBook.cvMonthlyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = false
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = false
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = true
            type_cost = "bulan"
        }
        bindingBottomsheetTimeBook.cbDailyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = true
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = false
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = false
            type_cost = "hari"
        }
        bindingBottomsheetTimeBook.cbWeeklyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = false
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = true
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = false
            type_cost = "minggu"
        }
        bindingBottomsheetTimeBook.cbMonthlyCost.setOnClickListener {
            bindingBottomsheetTimeBook.cbDailyCost.isChecked = false
            bindingBottomsheetTimeBook.cbWeeklyCost.isChecked = false
            bindingBottomsheetTimeBook.cbMonthlyCost.isChecked = true
            type_cost = "bulan"
        }
    }

}