package com.binar.kos.view.ui.history

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.kos.data.remote.response.historyResponse.HistoryResponse
import com.binar.kos.databinding.ActivityHistoryBinding
import com.binar.kos.utils.Status
import com.binar.kos.utils.toCapital
import com.binar.kos.view.adapter.HistoryAdapter
import com.binar.kos.view.ui.detailPesanan.DetailPesananActivity
import com.binar.kos.view.ui.detailTransaksi.DetailTransaksiActivity
import com.binar.kos.view.ui.favorit.FavoritActivity
import com.binar.kos.view.ui.logout.LogoutActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val mainViewModel: MainViewModel by viewModel()
    private val dataStore: DatastoreViewModel by viewModel()
    private lateinit var historyAdapter: HistoryAdapter

    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
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
                getHistory("success")
            }
            2 -> {
                getHistory("cancel")
            }
            3 -> {
                getHistory("pending")
            }
            4 -> {
                getHistory("failed")
            }
        }

    }

    private fun onDetailTransaction(item: HistoryResponse) {
        val intent = Intent(this, DetailTransaksiActivity::class.java)
        intent.putExtra(BOOKING_ID, item.id)
        intent.putExtra(TRANSACTION_STATUS, item.statusTransaction?.status)
        intent.putExtra(TRANSACTION_ROOM_COST, item.roomCost)
        intent.putExtra(TRANSACTION_FEE_COST, item.services)
        intent.putExtra(TRANSACTION_TOTAL_COST, item.totalCost)
        intent.putExtra(TRANSACTION_TYPE_COST, item.typeCost)
        intent.putExtra(TRANSACTION_STATUS_DESCRIPTION, item.statusTransaction?.description)
        startActivity(intent)
    }

    private fun onDetailOrder(item: HistoryResponse) {
        val intent = Intent(this, DetailPesananActivity::class.java)
        intent.putExtra(BOOKING_ID, item.id)
        intent.putExtra(TRANSACTION_STATUS, item.statusTransaction?.status)
        intent.putExtra(TRANSACTION_DATE, item.dateTransaction)
        intent.putExtra(TRANSACTION_METHOD, item.method)
        intent.putExtra(TRANSACTION_TYPE_COST, item.typeCost)
        intent.putExtra(CHECK_IN, item.rentalDate)
        Log.d("haha", item.rentalDate.toString())
        intent.putExtra(TYPE_ROOM, item.type)
        intent.putExtra(TITLE_ROOM, item.title)
        intent.putExtra(IMAGE_ROOM, item.imageUrl?.get(0)?.url)
        intent.putExtra(LOCATION_ROOM, "${item.address?.district!!.toCapital()}, ${item.address.city!!.toCapital()}")
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun getHistory(status: String?) {
        mainViewModel.getHistory(
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
                            "success" -> {
                                binding.tvTitleEmptyHistory.text = "Belum Ada Transaksi Berhasil"
                                binding.tvEmptyNotif.text =
                                    "Transaksimu belum ada yang berhasil nih.\n Yuk, selesaikan transaksimu!"
                            }
                            "cancel" -> {
                                binding.tvTitleEmptyHistory.text = "Belum Ada Transaksi Dibatalkan"
                                binding.tvEmptyNotif.text =
                                    "Transaksimu belum ada yang dibatalkan nih.\n Yuk, pertahankan ya!"
                            }
                            "pending" -> {
                                binding.tvTitleEmptyHistory.text = "Belum Ada Transaksi Pending"
                                binding.tvEmptyNotif.text =
                                    "Transaksimu belum ada yang pending nih.\n Yuk, buat transaksi pertamamu!"
                            }
                            "failed" -> {
                                binding.tvTitleEmptyHistory.text = "Belum Ada Transaksi Gagal"
                                binding.tvEmptyNotif.text =
                                    "Transaksimu belum ada yang gagal kok.\n Yuk, pertahankan!"
                            }
                        }
                        binding.cvEmptyHistory.isVisible = true
                    } else {
                        binding.rvHistory.isVisible = true
                        historyAdapter =
                            HistoryAdapter(response,
                                { item -> onDetailTransaction(item) },
                                { item -> onDetailOrder(item) })
                        val linearLayoutManager = LinearLayoutManager(this)
                        binding.rvHistory.layoutManager = linearLayoutManager
                        binding.rvHistory.adapter = historyAdapter
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
        binding.btnFavorite.setOnClickListener {
            val intent = Intent(this, FavoritActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    companion object{
        const val TRANSACTION_STATUS = "transaction_status"
        const val TRANSACTION_ROOM_COST = "transaction_room_cost"
        const val TRANSACTION_FEE_COST = "transaction_fee_cost"
        const val TRANSACTION_TOTAL_COST = "transaction_total_cost"
        const val TRANSACTION_TYPE_COST = "transaction_type_cost"
        const val TRANSACTION_STATUS_DESCRIPTION = "transaction_status_description"
        const val BOOKING_ID = "booking_id"


        const val TRANSACTION_DATE = "transaction_date"
        const val TRANSACTION_METHOD = "transaction_method"
        const val CHECK_IN = "check_in"
        const val TYPE_ROOM = "type_room"
        const val TITLE_ROOM = "title_room"
        const val LOCATION_ROOM = "location_room"
        const val IMAGE_ROOM = "imager_room"

    }
}