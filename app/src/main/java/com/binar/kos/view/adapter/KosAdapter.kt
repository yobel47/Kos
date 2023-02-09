package com.binar.kos.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Kos
import com.binar.kos.databinding.KosCardLayoutBinding
import com.binar.kos.utils.toCapital
import com.binar.kos.utils.toRp
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.room.RoomActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class KosAdapter(private val kosList: ArrayList<Kos>, private val context: Context):
    RecyclerView.Adapter<KosAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: KosCardLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KosAdapter.ViewHolder {
        val binding = KosCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(kosList[position]) {
                binding.kosName.text = kosList[position].title!!.toCapital()
                binding.kosLocation.text = kosList[position].address?.city!!.toCapital()
                binding.kosRate.text = "4.5" //TODO: find out more about Kos Rating

                if(kosList[position].discount?.isDiscount == "true"){
                    val price = kosList[position].price?.costMonth!!.toInt()
                    val discount = kosList[position].discount!!.discountPercentage!!.toInt()
                    val discountPrice = price / (discount * 100)
                    val truePrice = price - discountPrice
                    binding.kosOriginalPrice.text = price.toRp()
                    binding.kosOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    binding.kosDiscount.text  = "$discount%"
                    binding.kosDiscountedPrice.text = truePrice.toRp()
                }else{
                    binding.kosDiscountedPrice.text = "${kosList[position].price?.costMonth!!.toInt().toRp()}/bulan"
                    binding.kosDiscount.isVisible = false
                    binding.kosOriginalPrice.isVisible = false
                }

                binding.tvTypeHome.text = kosList[position].type!!.toCapital()
                binding.tvRoomLeftHome.text = "Sisa ${kosList[position].stock!!} kamar"


                if(kosList[position].imageUrl!!.isNotEmpty()){
                    val kosImageResponse = kosList[position].imageUrl?.get(0)
                    val requestOptions = RequestOptions().placeholder(R.drawable.kos_dummy_image)
                    Glide.with(context).load(kosImageResponse).apply(requestOptions).skipMemoryCache(true)
                        .into(binding.kosImagePreview)
                }

                binding.root.setOnClickListener {
                    val intent = Intent(context, RoomActivity::class.java)
                    intent.putExtra(HomeActivity.ID_ROOM,kosList[position].id.toString())
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return kosList.size
    }
}