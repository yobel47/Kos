package com.binar.kos.view.ui.detailTransaksi

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.binar.kos.databinding.ActivityDetailTransaksiBinding
import com.binar.kos.databinding.CancelDialogBinding
import com.binar.kos.utils.*
import com.binar.kos.view.ui.booking.BookingActivity
import com.binar.kos.view.ui.history.HistoryActivity
import com.binar.kos.view.ui.transaction.TransactionActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.RoomViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTransaksiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTransaksiBinding
    private lateinit var isdialogg: AlertDialog
    private val roomViewModel: RoomViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()
    var bookingId = 0
    var status = ""
    var roomCost = 0
    var feeCost = 0
    var totalCost = 0
    var typeCost = ""
    var statusDesc = ""
    var accessToken = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        onSetup()
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun onSetup() {
        bookingId = intent.getIntExtra(HistoryActivity.BOOKING_ID, 0)
        status = intent.getStringExtra(HistoryActivity.TRANSACTION_STATUS).toString()
        roomCost = intent.getIntExtra(HistoryActivity.TRANSACTION_ROOM_COST, 0)
        feeCost = intent.getIntExtra(HistoryActivity.TRANSACTION_FEE_COST, 0)
        totalCost = intent.getIntExtra(HistoryActivity.TRANSACTION_TOTAL_COST, 0)
        typeCost = intent.getStringExtra(HistoryActivity.TRANSACTION_TYPE_COST).toString()
        statusDesc = intent.getStringExtra(HistoryActivity.TRANSACTION_STATUS_DESCRIPTION).toString()
        binding.tvCostPaymentPrice.text = roomCost.toRp()
        binding.tvCostFeePrice.text = feeCost.toRp()
        binding.tvCostTotalPrice.text = totalCost.toRp()
        binding.btnPay.visibility = View.GONE
        binding.btnCancelBook.visibility = View.GONE
        when(status){
            "pending" -> {
                binding.cvStatusPending.visibility = View.VISIBLE
                binding.tvType.text = "Pending"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_pending)
                if(statusDesc=="pendingpayment"){
                    binding.btnPay.visibility = View.VISIBLE
                    binding.btnCancelBook.visibility = View.VISIBLE
                }else{
                    binding.tvStatusMessage.text = "Pemesananmu sedang diproses. Silahkan tunggu untuk proses selanjutnya!"
                    binding.btnPay.visibility = View.GONE
                    binding.btnCancelBook.visibility = View.GONE
                }
            }
            "waitpending" -> {
                binding.cvStatusPending.visibility = View.VISIBLE
                binding.tvType.text = "Pending"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_pending)
                binding.tvStatusMessage.text = "Pemesananmu sedang diproses. Silahkan tunggu untuk proses selanjutnya!"
            }
            "reject" -> {
                binding.cvStatusPending.visibility = View.VISIBLE
                binding.tvType.text = "Gagal"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_abort)
                binding.tvStatusMessage.text = "Pemesananmu gagal nih!"
            }
            "failed" -> {
                binding.cvStatusPending.visibility = View.VISIBLE
                binding.tvType.text = "Gagal"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_abort)
                binding.tvStatusMessage.text = "Pemesananmu gagal nih!"
            }
            "cancel" -> {
                binding.cvStatusCancel.visibility = View.VISIBLE
                binding.tvType.text = "Dibatalkan"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_cancel)
                binding.tvCancelMessage.text = statusDesc
            }
            "success" -> {
                binding.cvStatusSuccess.visibility = View.VISIBLE
                binding.tvType.text = "Berhasil"
                binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_success)
            }
        }

        binding.btnPay.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            intent.putExtra(BookingActivity.BOOKING_ID, bookingId)
            intent.putExtra(BookingActivity.BOOKING_ROOM_COST, roomCost)
            intent.putExtra(BookingActivity.BOOKING_FEE_COST, feeCost)
            intent.putExtra(BookingActivity.BOOKING_TOTAL_COST, totalCost)
            intent.putExtra(BookingActivity.BOOKING_ROOM_INFO, "Biaya Kost")
            startActivity(intent)
        }

        binding.btnCancelBook.setOnClickListener {
            val binding: CancelDialogBinding =
                CancelDialogBinding.inflate(LayoutInflater.from(this))
            val builder = AlertDialog.Builder(this)
            builder.setView(binding.root)
            builder.setCancelable(true)
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            isdialogg = builder.create()
            isdialogg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isdialogg.show()
            val window: Window = isdialogg.window!!
            val wlp: WindowManager.LayoutParams = window.attributes
            wlp.width = (width * 0.8).toInt()
            window.attributes = wlp
            binding.btnCancel.setOnClickListener {
                roomViewModel.cancelTransaction(
                    accessToken,
                    bookingId,
                    binding.etCancel.editText?.text.toString()
                ).observe(this) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                            showLoading(this)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            isdialogg.dismiss()
                            finish()
                            Toast.makeText(this, "Pemesanan berhasil dibatalkan", Toast.LENGTH_SHORT).show()
                        }
                        Status.ERROR -> {
                            hideLoading()
                            Toast.makeText(this,
                                "${result.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}