package com.binar.kos.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.remote.response.historyPenyewa.HistoryPenyewaResponse
import com.binar.kos.data.remote.response.myRoom.MyRoomResponse
import com.binar.kos.databinding.CardHistoryPenyewaBinding
import com.binar.kos.databinding.CardRoomBinding
import com.binar.kos.utils.toCapital
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class MyRoomAdapter(
    var roomList: List<MyRoomResponse>,
) : RecyclerView.Adapter<MyRoomAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardRoomBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NewApi", "SimpleDateFormat", "SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(roomList[position]) {
                binding.tvTitle.text = this.title?.toCapital()
                binding.tvLocation.text =
                    "${this.address?.district!!.toCapital()}, ${this.address.city!!.toCapital()}"

                if(this.approved?.isApprovalRoom==false){
                    binding.tvType.text = "Pending"
                    binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_pending)
                }else{
                    binding.tvType.text = "Diterima"
                    binding.tvType.background = binding.root.context.getDrawable(com.binar.kos.R.drawable.tag_success)
                }

                Glide.with(itemView.context)
                    .load(this.imageUrl?.get(0)?.url.toString())
                    .into(binding.ivRoom)
            }
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }
}