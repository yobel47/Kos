package com.binar.kos.view.ui.detailPesanan

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.kos.databinding.ActivityDetailPesananBinding
import com.binar.kos.utils.toCapital
import com.binar.kos.view.ui.history.HistoryActivity
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class DetailPesananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPesananBinding

    var bookingId = 0
    var status = ""
    var date = ""
    var method = ""
    var typeCost = ""
    var checkIn = ""
    var typeRoom = ""
    var title = ""
    var location = ""
    var image = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onSetup()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SimpleDateFormat", "UseCompatLoadingForDrawables", "SetTextI18n")
    private fun onSetup() {
        bookingId = intent.getIntExtra(HistoryActivity.BOOKING_ID, 0)
        status = intent.getStringExtra(HistoryActivity.TRANSACTION_STATUS).toString()
        date = intent.getStringExtra(HistoryActivity.TRANSACTION_DATE).toString()
        method = intent.getStringExtra(HistoryActivity.TRANSACTION_METHOD).toString()
        typeCost = intent.getStringExtra(HistoryActivity.TRANSACTION_TYPE_COST).toString()
        checkIn = intent.getStringExtra(HistoryActivity.CHECK_IN).toString()
        typeRoom = intent.getStringExtra(HistoryActivity.TYPE_ROOM).toString()
        title = intent.getStringExtra(HistoryActivity.TITLE_ROOM).toString()
        location = intent.getStringExtra(HistoryActivity.LOCATION_ROOM).toString()
        image = intent.getStringExtra(HistoryActivity.IMAGE_ROOM).toString()



        binding.tvIdRent.text = bookingId.toString()
        when(status){
            "success" -> {
                binding.tvType.text = "Berhasil"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_success)
            }
            "cancel" -> {
                binding.tvType.text = "Dibatalkan"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_cancel)
            }
            "pending" -> {
                binding.tvType.text = "Pending"

                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_pending)
            }
            "waitpending" -> {
                binding.tvType.text = "Pending"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_pending)
            }
            "failed" -> {
                binding.tvType.text = "Gagal"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_abort)
            }
            "reject" -> {
                binding.tvType.text = "Gagal"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_abort)
            }
        }
        binding.tvDateRent.text = date
        binding.tvPaymentMethod.text = method
        binding.tvTitle.text = title
        binding.tvLocation.text = location


        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT-7")
        val date = sdf.parse(checkIn)
        val sdf1 = SimpleDateFormat("EEEE, dd MMMM yyyy")
        val date2 = sdf1.format(date?.time)
        binding.tvCheckin.text = date2.toCapital()

        binding.tvTypeRent.text = typeCost.toCapital()
        binding.tvTypeRoom.text = typeRoom.toCapital()

        Glide.with(this)
            .load(image)
            .into(binding.ivRoom)
    }
}