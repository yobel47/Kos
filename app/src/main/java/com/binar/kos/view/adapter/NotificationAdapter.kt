package com.binar.kos.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.remote.response.NotificationResponse
import com.binar.kos.databinding.CardNotifBinding
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(
    var notifList: List<NotificationResponse>,
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardNotifBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NewApi", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(notifList[position]) {
                binding.tvTitleNotif.text = this.title
                binding.tvMessageNotif.text = this.body

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                sdf.timeZone = TimeZone.getTimeZone("GMT+7")
                val date = sdf.parse(this.createdAt!!)
                val sdf1 = SimpleDateFormat("EEEE, dd MMMM yyyy")
                val sdf2 = SimpleDateFormat("HH:mm")
                val date2 = sdf1.format(date?.time)
                val time2 = sdf2.format(date?.time)
                binding.tvDateNotif.text = date2
                binding.tvTimeNotif.text = time2
            }
        }
    }

    override fun getItemCount(): Int {
        return notifList.size
    }
}