package com.binar.kos.view.ui.transaction

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.remote.response.paymentMethod.PaymentMethodResponseItem
import com.binar.kos.databinding.ActivityTransactionBinding
import com.binar.kos.databinding.ActivityVerificationBinding
import com.binar.kos.utils.Status
import com.binar.kos.utils.hideLoading
import com.binar.kos.utils.showLoading
import com.binar.kos.utils.toRp
import com.binar.kos.view.adapter.PaymentMethodAdapter
import com.binar.kos.view.adapter.RoomAdapter.BottomFacilitiesRoomAdapter
import com.binar.kos.view.adapter.RoomAdapter.FacilitiesRoomAdapter
import com.binar.kos.view.ui.booking.BookingActivity
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.room.RoomActivity
import com.binar.kos.view.ui.transactionGuide.TransactionGuideActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.HomeViewModel
import com.binar.kos.viewmodel.RoomViewModel
import com.google.android.material.card.MaterialCardView
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding
    private val dataStore: DatastoreViewModel by viewModel()
    private val roomViewModel: RoomViewModel by viewModel()
    private lateinit var paymentMethodAdapter: PaymentMethodAdapter
    var bookingId = 0
    var roomCost = 0
    var feeCost = 0
    var totalCost = 0
    var paymentId = 0
    var paymentMethod = ""
    var paymentBankType = ""
    var paymentContent = ""
    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onSetup()
        getPaymentData()

        binding.btnPay.isEnabled = false

        binding.btnBack.setOnClickListener {
            finish()
        }


        binding.btnPay.setOnClickListener {
            onPayment()
        }
    }

    private fun onSetup() {
        bookingId = intent.getIntExtra(BookingActivity.BOOKING_ID, 0)
        roomCost = intent.getIntExtra(BookingActivity.BOOKING_ROOM_COST, 0)
        feeCost = intent.getIntExtra(BookingActivity.BOOKING_FEE_COST, 0)
        totalCost = intent.getIntExtra(BookingActivity.BOOKING_TOTAL_COST, 0)
        val roomInfo = intent.getStringExtra(BookingActivity.BOOKING_ROOM_INFO)
        binding.tvCostPaymentPrice.text = roomCost.toRp()
        binding.tvCostFeePrice.text = feeCost.toRp()
        binding.tvCostTotalPrice.text = totalCost.toRp()
        binding.tvCostPayment.text = roomInfo
    }


    private fun onPayment() {
        roomViewModel.createTransaction(accessToken, bookingId, paymentId).observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading(this)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    val intent = Intent(this, TransactionGuideActivity::class.java)
                    intent.putExtra(BOOKING_ID, bookingId)
                    intent.putExtra(PAYMENT_ID, paymentId)
                    intent.putExtra(PAYMENT_METHOD, paymentMethod)
                    intent.putExtra(PAYMENT_BANK_TYPE, paymentBankType)
                    intent.putExtra(PAYMENT_CONTENT, paymentContent)
                    startActivity(intent)
                }
                Status.ERROR -> {
                    showLoading(this)
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    private fun onClickMethod(data: PaymentMethodResponseItem, view: View) {
        paymentId = data.id!!
        paymentMethod = data.method!!
        paymentBankType = data.bankType!!
        paymentContent = data.content!!
        binding.btnPay.isEnabled = true
    }


    private fun getPaymentData() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
                roomViewModel.getPaymentMethod(token).observe(this) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                            showLoading(this)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            val layoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this)
                            binding.rvPaymentMethod.layoutManager = layoutManager
                            paymentMethodAdapter =
                                PaymentMethodAdapter(result.data!!) { method, view ->
                                    onClickMethod(method, view)
                                }
                            binding.rvPaymentMethod.adapter = paymentMethodAdapter
                        }
                        Status.ERROR -> {
                            showLoading(this)
                            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val BOOKING_ID = "booking_id"
        const val PAYMENT_ID = "payment_id"
        const val PAYMENT_METHOD = "payment_method"
        const val PAYMENT_BANK_TYPE = "payment_bank_type"
        const val PAYMENT_CONTENT = "payment_content"
    }
}