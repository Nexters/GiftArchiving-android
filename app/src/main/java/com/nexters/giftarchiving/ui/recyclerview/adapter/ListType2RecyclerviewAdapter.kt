package com.nexters.giftarchiving.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.model.GiftListResponse
import com.nexters.giftarchiving.model.GiftResponse
import java.time.format.DateTimeFormatter

class ListType2RecyclerviewAdapter(private val context: Context, private val gifts : List<GiftResponse>, val isReceived : Boolean) : RecyclerView.Adapter<ListType2RecyclerviewAdapter.ItemViewHolder>() {

    var mPosition = 0

    fun getPosition() : Int{
        return mPosition
    }

    private fun setPosition(position: Int){
        mPosition = position
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemImageView: ImageView = itemView.findViewById(R.id.grid_image)
        private val personTextView: TextView = itemView.findViewById(R.id.grid_person)
        private val dateTextView: TextView = itemView.findViewById(R.id.grid_date)

        fun bind(gift : GiftResponse, position: Int) {
            Glide.with(context).load(gift.giftImgUrl).into(itemImageView)
            itemImageView.clipToOutline = true
            if(isReceived){
                personTextView.text = String.format("From. %s",gift.giftName)
            } else{
                personTextView.text = String.format("To. %s",gift.giftName)
            }
            val date = String.format("%s.%s.%s",gift.giftReceiveDate.substring(0,4),gift.giftReceiveDate.substring(5,7),gift.giftReceiveDate.substring(8,10))
            dateTextView.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_layout, parent, false)
        return ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(gifts[position], position)
    }

    override fun getItemCount(): Int = gifts.size
}
