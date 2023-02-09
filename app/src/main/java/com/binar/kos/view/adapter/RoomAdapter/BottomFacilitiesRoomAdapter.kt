package com.binar.kos.view.adapter.RoomAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.remote.response.roomResponse.Facilities
import com.binar.kos.databinding.CardFacilitiesRoomBinding

class BottomFacilitiesRoomAdapter(
    private var facilities: List<Filter>,
) : RecyclerView.Adapter<BottomFacilitiesRoomAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardFacilitiesRoomBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardFacilitiesRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(facilities[position]) {
                binding.icIc.setImageResource(this.icon)
                binding.tvFacilities.text = this.text
            }
        }
    }

    override fun getItemCount(): Int {
        return facilities.size
    }


}