package com.binar.kos.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.remote.response.searchResponse.SearchResponse
import com.binar.kos.databinding.CardSearchItemBinding
import com.binar.kos.utils.toRp
import com.bumptech.glide.Glide

class SearchResultAdapter(
    val clickListener: (SearchResponse) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<SearchResponse>() {
        override fun areItemsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SearchResponse,
            newItem: SearchResponse
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(item: List<SearchResponse>) = differ.submitList(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: CardSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: SearchResponse) {
            binding.root.setOnClickListener {
                clickListener(item)
            }

            binding.tvTitle.text = item.title
            binding.tvLocation.text = "${item.address?.district}, ${item.address?.city}"
            binding.tvType.text = item.type

            binding.tvPrice.text = "${item.price?.costMonth!!.toInt().toRp()}/bulan"

            if(item.discount?.isDiscount == "true"){
                val price = item.price.costMonth.toInt()
                val discount = item.discount.discountPercentage!!.toInt()
                val discountPrice = price * (discount / 100)
                binding.tvDiscountPrice.text = "Rp.${discountPrice.toString()}"
                binding.tvDiscount.text = discount.toString()
            }else{
                binding.tvDiscountPrice.isVisible = false
                binding.tvDiscount.isVisible = false
            }

            binding.tvRoomLeft.text = "Sisa ${item.stock} kamar"

//            Glide.with(itemView.context)
//                .load(item.imageUrl?.get(0))
//                .into(binding.ivRoom)

        }
    }
}