package com.binar.kos.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Kos
import com.binar.kos.utils.toCapital
import com.binar.kos.utils.toRp
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
        var category: TextView = itemView.findViewById<TextView>(R.id.tv_type_home)
        var kosImagePreview: ImageView = itemView.findViewById<ImageView>(R.id.kosImagePreview)
        var roomLeft: TextView = itemView.findViewById<TextView>(R.id.tv_room_left_home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kos_card_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = kosList[position].title!!.toCapital()
        holder.locationText.text = kosList[position].address?.city!!.toCapital()
        holder.rateText.text = "4.5" //TODO: find out more about Kos Rating

        if(kosList[position].discount?.isDiscount == "true"){
            val price = kosList[position].price?.costMonth!!.toInt()
            val discount = kosList[position].discount!!.discountPercentage!!.toInt()
            val discountPrice = price / (discount * 100)
            val truePrice = price - discountPrice
            holder.originalPriceText.text = price.toRp()
            holder.originalPriceText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.discountText.text  = "${discount.toString()}%"
            holder.discountedPriceText.text = truePrice.toRp()
        }else{
            holder.discountedPriceText.text = "${kosList[position].price?.costMonth!!.toInt().toRp()}/bulan"
            holder.discountText.isVisible = false
            holder.originalPriceText.isVisible = false
        }

        holder.category.text = kosList[position].type!!.toCapital()
        holder.roomLeft.text = "Sisa ${kosList[position].stock!!} kamar"


        if(kosList[position].imageUrl!!.isNotEmpty()){
            val kosImageResponse = kosList[position].imageUrl?.get(0)
            val requestOptions = RequestOptions().placeholder(R.drawable.kos_dummy_image)
            Glide.with(context).load(kosImageResponse).apply(requestOptions).skipMemoryCache(true)
                .into(holder.kosImagePreview)
        }

    }

    override fun getItemCount(): Int {
        return kosList.size
    }
}