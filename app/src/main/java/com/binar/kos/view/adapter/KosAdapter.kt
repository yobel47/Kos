package com.binar.kos.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Kos
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class KosAdapter(private val kosList: ArrayList<Kos>, private val context: Context):
    RecyclerView.Adapter<KosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameText: TextView = itemView.findViewById<TextView>(R.id.kosName)
        var locationText: TextView = itemView.findViewById<TextView>(R.id.kosLocation)
        var rateText: TextView = itemView.findViewById<TextView>(R.id.kosLocation)
        var discountText: TextView = itemView.findViewById<TextView>(R.id.kosDiscount)
        var originalPriceText: TextView = itemView.findViewById<TextView>(R.id.kosOriginalPrice)
        var discountedPriceText: TextView = itemView.findViewById<TextView>(R.id.kosDiscountedPrice)
        var category: Button = itemView.findViewById<Button>(R.id.kosCategory)
        var kosImagePreview: ImageView = itemView.findViewById<ImageView>(R.id.kosImagePreview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kos_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = kosList[position].name
        holder.locationText.text = kosList[position].kota
        holder.rateText.text = kosList[position].rating.toString()
        holder.discountText.text = "${kosList[position].discount}%"
        holder.originalPriceText.text = kosList[position].originalPrice.toString()
        holder.discountedPriceText.text = kosList[position].discount.toString()
        holder.category.text = kosList[position].tipeKos


        val kosImageResponse = kosList[position].kosImage
        val requestOptions = RequestOptions().placeholder(R.drawable.kos_dummy_image)
        Glide.with(context).load(kosImageResponse).apply(requestOptions).skipMemoryCache(true)
            .into(holder.kosImagePreview)
    }

    override fun getItemCount(): Int {
        return kosList.size
    }
}