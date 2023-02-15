package com.binar.kos.view.ui.detailPenyewa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.kos.databinding.ActivityDetailPenyewaBinding
import com.binar.kos.utils.toCapital
import com.binar.kos.view.ui.historyPenyewa.HistoryPenyewaActivity
import java.text.SimpleDateFormat
import java.util.*

class DetailPenyewaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPenyewaBinding

    var bookingId = 0
    var bookingDate = ""
    var rentalDate = ""
    var durationType = ""
    var durationRent = 0
    var countRent = 0
    var noteRent = ""
    var name = ""
    var gender = ""
    var birthdate = ""
    var status = ""
    var telp = ""
    var job = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenyewaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onSetup()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SimpleDateFormat", "UseCompatLoadingForDrawables", "SetTextI18n")
    private fun onSetup() {
        bookingId = intent.getIntExtra(HistoryPenyewaActivity.BOOKING_ID, 0)
        bookingDate = intent.getStringExtra(HistoryPenyewaActivity.BOOKING_DATE).toString()
        rentalDate = intent.getStringExtra(HistoryPenyewaActivity.RENTAL_DATE).toString()
        durationRent = intent.getIntExtra(HistoryPenyewaActivity.RENTAL_DATE, 1)
        durationType = intent.getStringExtra(HistoryPenyewaActivity.DURATION_TYPE).toString()
        countRent = intent.getIntExtra(HistoryPenyewaActivity.COUNT_RENT, 1)
        noteRent = intent.getStringExtra(HistoryPenyewaActivity.NOTE_RENT ).toString()
        name = intent.getStringExtra(HistoryPenyewaActivity.NAME_BOOKER).toString()
        gender = intent.getStringExtra(HistoryPenyewaActivity.GENDER_BOOKER ).toString()
        birthdate = intent.getStringExtra(HistoryPenyewaActivity.BIRTHDATE_BOOKER ).toString()
        status = intent.getStringExtra(HistoryPenyewaActivity.STATUS_BOOKER ).toString()
        telp = intent.getStringExtra(HistoryPenyewaActivity.TELP_BOOKER ).toString()
        job = intent.getStringExtra(HistoryPenyewaActivity.JOB_BOOKER ).toString()

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT+7")
        val date = sdf.parse(bookingDate)
        val sdf1 = SimpleDateFormat("dd MMMM yyyy")
        val date2 = sdf1.format(date?.time)
        binding.tvDateBook.text = date2

        val date3 = sdf.parse(rentalDate)
        val date4 = sdf1.format(date3?.time)
        binding.tvDateRent.text = date4

        binding.tvDurationRent.text = "${durationRent} $durationType"

        binding.tvCountRenter.text = countRent.toString()
        if (noteRent=="null"){
            binding.tvNote.text = "-"
        }else{
            binding.tvNote.text = noteRent
        }

        binding.tvName.text = name.toCapital()
        binding.tvGender.text = gender.toCapital()
        binding.tvTanggalLahir.text= birthdate
        binding.tvStatus.text = status.toCapital()
        binding.tvNotelp.text = telp
        binding.tvProfesion.text = job.toCapital()

    }
}