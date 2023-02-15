package com.binar.kos.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.remote.response.historyResponse.HistoryResponse
import com.binar.kos.databinding.CardHistoryBinding
import com.binar.kos.utils.toCapital
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    var historyList: List<HistoryResponse>,
    val clickDetailTransaction: (HistoryResponse) -> Unit,
    val clickDetailOrder: (HistoryResponse) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NewApi", "SimpleDateFormat", "SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(historyList[position]) {
                binding.tvTitle.text = this.title
                binding.tvLocation.text =
                    "${this.address?.district!!.toCapital()}, ${this.address.city!!.toCapital()}"

                when (this.statusTransaction?.status.toString()) {
                    "success" -> {
                        binding.tvType.text = "Berhasil"
                        binding.btnDetailPesanan.visibility = View.VISIBLE
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

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                sdf.timeZone = TimeZone.getTimeZone("GMT-7")
                val date = sdf.parse(this.bookingDate!!)
                val sdf1 = SimpleDateFormat("dd MMMM yyyy")
                val date2 = sdf1.format(date?.time)
                binding.tvTitleDate.text = date2

                binding.btnDetailPesanan.setOnClickListener {
                    clickDetailOrder(this)
                }
                binding.btnDetailTransaksi.setOnClickListener {
                    clickDetailTransaction(this)
                }

                Glide.with(itemView.context)
                    .load(this.imageUrl?.get(0)?.url.toString())
                    .into(binding.ivRoom)
            }
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}