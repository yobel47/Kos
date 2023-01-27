package com.binar.kos.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.databinding.CardFilterBinding
import com.binar.kos.utils.RecyclerViewClickListener

class FilterGeneralAdapter(
    var filterList: List<Filter>,
    val clickListener: (Filter) -> Unit
) : RecyclerView.Adapter<FilterGeneralAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardFilterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(filterList[position]) {
                binding.tvFilter.text = this.text
                binding.ivIconFilter.setImageResource(this.icon)
                binding.root.setOnClickListener {
                    clickListener(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return filterList.size
    }
}