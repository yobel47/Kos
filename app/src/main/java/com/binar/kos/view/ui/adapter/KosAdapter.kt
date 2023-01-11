package com.binar.kos.view.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Kos

class KosAdapter(private val kosList: ArrayList<Kos>):
    RecyclerView.Adapter<KosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameText: TextView = itemView.findViewById<TextView>(R.id.kosName)
        var locationText: TextView = itemView.findViewById<TextView>(R.id.kosLocation)
        var rateText: TextView = itemView.findViewById<TextView>(R.id.kosLocation)
        var discountText: TextView = itemView.findViewById<TextView>(R.id.kosDiscount)
        var originalPriceText: TextView = itemView.findViewById<TextView>(R.id.kosOriginalPrice)
        var discountedPriceText: TextView = itemView.findViewById<TextView>(R.id.kosDiscountedPrice)
        var category: Button = itemView.findViewById<Button>(R.id.kosCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kos_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = kosList[position].name
        holder.locationText.text = kosList[position].kota
        holder.rateText.text = kosList[position].rating.toString()
        holder.discountText.text = kosList[position].discount.toString()
        holder.originalPriceText.text = kosList[position].originalPrice.toString()
        holder.discountedPriceText.text = kosList[position].discount.toString()
        holder.category.text = kosList[position].tipeKos.toString()
    }

    override fun getItemCount(): Int {
        return kosList.size
    }
}