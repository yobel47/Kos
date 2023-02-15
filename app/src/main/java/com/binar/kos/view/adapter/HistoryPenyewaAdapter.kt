package com.binar.kos.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.remote.response.historyPenyewa.HistoryPenyewaResponse
import com.binar.kos.databinding.CardHistoryPenyewaBinding
import com.binar.kos.utils.toCapital
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class HistoryPenyewaAdapter(
    var historyList: List<HistoryPenyewaResponse>,
    val clickTerima: (HistoryPenyewaResponse) -> Unit,
    val clickTolak: (HistoryPenyewaResponse) -> Unit,
    val clickDetailPenyewa: (HistoryPenyewaResponse) -> Unit
) : RecyclerView.Adapter<HistoryPenyewaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardHistoryPenyewaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardHistoryPenyewaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NewApi", "SimpleDateFormat", "SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(historyList[position]) {
                binding.tvTitle.text = this.rooms?.title
                binding.tvLocation.text =
                    "${this.rooms?.address?.district!!.toCapital()}, ${this.rooms.address.city!!.toCapital()}"

                when (this.rentalAgreement?.statusBooking.toString()) {
                    "simpan" -> {
                        binding.tvType.text = "Pending"
                        binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_pending)
                    }
                    "ajukan" -> {
                        binding.tvType.text = "Pending"
                        binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_pending)
                        binding.cvTerimaTolak.visibility = View.VISIBLE
                    }
                    "disetujui" -> {
                        binding.tvType.text = "Pending"
                        binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_pending)
                        binding.cvWaitPembayaran.visibility = View.VISIBLE
                    }
                    "berhasil" -> {
                        binding.tvType.text = "Berhasil"
                        binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_success)
                    }
                    "ditolak" -> {
                        binding.tvType.text = "Dibatalkan"
                        binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_cancel)
                    }
                    "gagal" -> {
                        binding.tvType.text = "Gagal"
                        binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_abort)
                    }
                }

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                sdf.timeZone = TimeZone.getTimeZone("GMT+7")
                val date = sdf.parse(this.bookingDate!!)
                val sdf1 = SimpleDateFormat("dd MMM yyyy")
                val date2 = sdf1.format(date?.time)
                binding.tvTitleDate.text = date2

                binding.btnDetailPenyewa.setOnClickListener {
                    clickDetailPenyewa(this)
                }
                binding.btnTerima.setOnClickListener {
                    clickTerima(this)
                }
                binding.btnTolak.setOnClickListener {
                    clickTolak(this)
                }

                Glide.with(itemView.context)
                    .load(this.rooms.imageUrl?.get(0)?.url.toString())
                    .into(binding.ivRoom)
            }
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}