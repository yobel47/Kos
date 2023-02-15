package com.binar.kos.view.ui.historyPenyewa

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.kos.data.remote.response.historyPenyewa.HistoryPenyewaResponse
import com.binar.kos.databinding.ActivityHistoryPenyewaBinding
import com.binar.kos.databinding.CancelDialogBinding
import com.binar.kos.utils.Status
import com.binar.kos.utils.hideLoading
import com.binar.kos.utils.showLoading
import com.binar.kos.view.adapter.HistoryPenyewaAdapter
import com.binar.kos.view.ui.add.AddRoomActivity
import com.binar.kos.view.ui.detailPenyewa.DetailPenyewaActivity
import com.binar.kos.view.ui.logout.LogoutActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.MainViewModel
import com.binar.kos.viewmodel.RoomViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryPenyewaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryPenyewaBinding
    private val mainViewModel: MainViewModel by viewModel()
    private val roomViewModel: RoomViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()
    private lateinit var historyPenyewaAdapter: HistoryPenyewaAdapter
    private lateinit var isdialogg: AlertDialog

    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPenyewaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
                getHistory(null)
            }
        }

        toHome()
        toProfile()
        toFavorit()

        binding.tvAll.setOnClickListener {
            setupOnClickHistoryType(0)
        }
        binding.tvSuccess.setOnClickListener {
            setupOnClickHistoryType(1)
        }
        binding.tvCancel.setOnClickListener {
            setupOnClickHistoryType(2)
        }
        binding.tvPending.setOnClickListener {
            setupOnClickHistoryType(3)
        }
        binding.tvAbort.setOnClickListener {
            setupOnClickHistoryType(4)
        }
    }

    private fun setupOnClickHistoryType(type: Int) {
        when (type) {
            0 -> {
                getHistory(null)
            }
            1 -> {
                getHistory("berhasil")
            }
            2 -> {
                getHistory("dibatalkan")
            }
            3 -> {
                getHistory("pending")
            }
            4 -> {
                getHistory("gagal")
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun onTolak(item: HistoryPenyewaResponse) {
        val binding: CancelDialogBinding =
            CancelDialogBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(binding.root)
        builder.setCancelable(true)
        binding.tvTittleCancel.text = "Menolak pesanan kos nih"
        binding.tvCancel.text =
            "Yahh.. Kamu ingin menolak pesanan Kost\nTolong tulis alasan pembatalan di bawah ini"
        binding.btnCancel.text = "Tolak"
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
            roomViewModel.approveBook(
                accessToken,
                item.id!!,
                "false",
                "ditolak",
                binding.etCancel.editText?.text.toString()
            ).observe(this) { result ->
                when (result.status) {
                    Status.LOADING -> {
                        showLoading(this)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        isdialogg.dismiss()
                        Toast.makeText(this, "Berhasil batalkan pesanan!", Toast.LENGTH_SHORT).show()
                        getHistory(null)
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

    private fun onTerima(item: HistoryPenyewaResponse) {
        roomViewModel.approveBook(
            accessToken,
            item.id!!,
            "true",
            "disetujui",
            "disetujui untuk booking"
        ).observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading(this)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Toast.makeText(this, "Berhasil terima pesanan!", Toast.LENGTH_SHORT).show()
                    getHistory(null)
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

    private fun onDetailOrder(item: HistoryPenyewaResponse) {
        val intent = Intent(this, DetailPenyewaActivity::class.java)
        intent.putExtra(BOOKING_ID, item.id)
        intent.putExtra(BOOKING_DATE, item.bookingDate)
        intent.putExtra(RENTAL_DATE, item.rentalDate)
        intent.putExtra(DURATION_RENT, item.duration)
        intent.putExtra(COUNT_RENT, item.totalUser)
        intent.putExtra(DURATION_TYPE, item.typeCost)
        intent.putExtra(NOTE_RENT, item.note)
        intent.putExtra(NAME_BOOKER, item.detailUser?.namaLengkap)
        intent.putExtra(GENDER_BOOKER, item.detailUser?.gender)
        intent.putExtra(BIRTHDATE_BOOKER, item.detailUser?.tanggalLahir)
        intent.putExtra(STATUS_BOOKER, item.detailUser?.status)
        intent.putExtra(TELP_BOOKER, item.detailUser?.noTelepon)
        intent.putExtra(JOB_BOOKER, item.detailUser?.profesi)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun getHistory(status: String?) {
        mainViewModel.getHistoryPenyewa(
            accessToken,
            status
        ).observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.rvHistory.isVisible = false
                    binding.cvEmptyHistory.isVisible = false
                    binding.pbRv.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.pbRv.isVisible = false
                    val response = result.data
                    if (response!!.isEmpty()) {
                        when (status) {
                            "berhasil" -> {
                                binding.tvTitleEmptyHistory.text = "Belum Ada Transaksi Berhasil"
                                binding.tvEmptyNotif.text =
                                    "Transaksimu belum ada yang berhasil nih.\n Yuk, selesaikan transaksimu!"
                            }
                            "dibatalkan" -> {
                                binding.tvTitleEmptyHistory.text = "Belum Ada Transaksi Dibatalkan"
                                binding.tvEmptyNotif.text =
                                    "Transaksimu belum ada yang dibatalkan nih.\n Yuk, pertahankan ya!"
                            }
                            "pending" -> {
                                binding.tvTitleEmptyHistory.text = "Belum Ada Transaksi Pending"
                                binding.tvEmptyNotif.text =
                                    "Transaksimu belum ada yang pending nih.\n Yuk, buat transaksi pertamamu!"
                            }
                            "gagal" -> {
                                binding.tvTitleEmptyHistory.text = "Belum Ada Transaksi Gagal"
                                binding.tvEmptyNotif.text =
                                    "Transaksimu belum ada yang gagal kok.\n Yuk, pertahankan!"
                            }
                        }
                        binding.cvEmptyHistory.isVisible = true
                    } else {
                        binding.rvHistory.isVisible = true
                        historyPenyewaAdapter =
                            HistoryPenyewaAdapter(response,
                                { item -> onTerima(item) },
                                { item -> onTolak(item) },
                                { item -> onDetailOrder(item) })
                        val linearLayoutManager = LinearLayoutManager(this)
                        binding.rvHistory.layoutManager = linearLayoutManager
                        binding.rvHistory.adapter = historyPenyewaAdapter
                    }
                }
                Status.ERROR -> {
                    binding.rvHistory.isVisible = false
                    binding.cvEmptyHistory.isVisible = true
                    binding.pbRv.isVisible = false
                    Toast.makeText(this,
                        "${result.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun toHome() {
        binding.btnHome.setOnClickListener {
            finish()
        }
    }

    private fun toProfile() {
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, LogoutActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun toFavorit() {
        binding.btnMyRoom.setOnClickListener {
            val intent = Intent(this, AddRoomActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val BOOKING_ID = "booking_id"
        const val BOOKING_DATE = "booking_date"
        const val RENTAL_DATE = "rental_date"
        const val DURATION_RENT = "duration_rent"
        const val DURATION_TYPE = "duration_type"
        const val COUNT_RENT = "count_rent"
        const val NOTE_RENT = "note_rent"

        const val NAME_BOOKER = "name_booker"
        const val GENDER_BOOKER = "gender_booker"
        const val BIRTHDATE_BOOKER = "birthdate_booker"
        const val STATUS_BOOKER = "status_booker"
        const val TELP_BOOKER = "telp_booker"
        const val JOB_BOOKER = "job_booker"
    }
}