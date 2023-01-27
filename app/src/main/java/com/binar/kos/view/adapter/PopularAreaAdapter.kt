package com.binar.kos.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.local.entity.PopularArea
import com.binar.kos.databinding.CardFilterBinding
import com.binar.kos.databinding.CardPopularAreaBinding
import com.binar.kos.utils.RecyclerViewClickListener

class PopularAreaAdapter(
    var popularAreaList: List<PopularArea>,
    val clickListener: (PopularArea) -> Unit
) : RecyclerView.Adapter<PopularAreaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardPopularAreaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardPopularAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(popularAreaList[position]) {
                binding.btnPopularArea.text = this.text
                binding.btnPopularArea.setOnClickListener {
                    clickListener(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return popularAreaList.size
    }
}