package com.binar.kos.view.adapter.RoomAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.local.entity.Review
import com.binar.kos.data.remote.response.roomResponse.Facilities
import com.binar.kos.databinding.CardFacilitiesRoomBinding
import com.binar.kos.databinding.CardReviewBinding

class ReviewRoomAdapter(
    private var reviewData: List<Review>,
) : RecyclerView.Adapter<ReviewRoomAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(reviewData[position]) {
                binding.tvReview.text = this.rating
                binding.tvProfileName.text = this.owner
                binding.tvTimeReview.text = this.time
            }
        }
    }

    override fun getItemCount(): Int {
        return reviewData.size
    }


}