package com.binar.kos.view.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.remote.response.paymentMethod.PaymentMethodResponseItem
import com.binar.kos.databinding.CardFacilitiesRoomBinding
import com.binar.kos.databinding.PaymentHeaderBinding
import com.bumptech.glide.Glide

class PaymentMethodAdapter(
    private var dataList: List<PaymentMethodResponseItem>,
    val clickListener: (PaymentMethodResponseItem, View) -> Unit,
) : RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {

    companion object {
        var TRANSFER = 0
        var KLIKPAY = 0
        var EWALLET = 0
        var QRIS = 0
        var isChecked = -1
        var isStart = 0
    }

    inner class ViewHolder(val binding: PaymentHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PaymentHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.tvHeaderPayment.visibility = View.GONE
                binding.view2.visibility = View.GONE

                if(this.id == 1){
                    binding.tvHeaderPayment.visibility = View.VISIBLE
                    binding.view2.visibility = View.VISIBLE
                    binding.tvHeaderPayment.text = this.method
                }
                if(this.id == 8){
                    binding.tvHeaderPayment.visibility = View.VISIBLE
                    binding.view2.visibility = View.VISIBLE
                    binding.tvHeaderPayment.text = this.method
                }
                if(this.id == 9){
                    binding.tvHeaderPayment.visibility = View.VISIBLE
                    binding.view2.visibility = View.VISIBLE
                    binding.tvHeaderPayment.text = this.method
                }
                if(this.id == 12){
                    binding.tvHeaderPayment.visibility = View.VISIBLE
                    binding.view2.visibility = View.VISIBLE
                    binding.tvHeaderPayment.text = this.method
                }



                Glide.with(itemView.context).load(this.picture)
                    .into(binding.icIc)
                binding.container.setBackgroundColor(ContextCompat.getColor(itemView.context,
                    R.color.background))
                binding.tvPaymentMethod.text = this.bankType
                binding.container.setOnClickListener {
                    clickListener(this, it)
                    if (isChecked != position) {
                        notifyItemChanged(isChecked)
                        isChecked = position
                        binding.container.setBackgroundColor(ContextCompat.getColor(itemView.context,
                            R.color.primary_container))
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}