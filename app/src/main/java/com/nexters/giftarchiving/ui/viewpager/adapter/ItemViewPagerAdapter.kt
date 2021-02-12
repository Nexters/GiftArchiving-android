package com.nexters.giftarchiving.ui.viewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.model.GiftListResponse
import com.nexters.giftarchiving.model.GiftResponse
import java.time.format.DateTimeFormatter

class ItemViewPagerAdapter(val context : Context, private val giftListResponse: GiftListResponse, private val viewType: Int) : RecyclerView.Adapter<ItemViewPagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemImageView: ImageView = itemView.findViewById(R.id.item_image)
        private val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.item_constraintLayout)
        private val personTextView: TextView = itemView.findViewById(R.id.item_person)
        private val dateTextView: TextView = itemView.findViewById(R.id.item_date)

        fun bind(gift : GiftResponse) {
            Glide.with(context).load(gift.giftImgUrl).into(itemImageView)
            if(viewType==1){
                constraintLayout.clipToOutline = true
                when(gift.bgColor){
                    "R.color.orange" -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_orange_background)
                    "R.color.blue" -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_blue_background)
                    "R.color.yellow" -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_yellow_background)
                    else -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_gray_background)
                }
            }
            personTextView.text = gift.giftName
            val formatter = DateTimeFormatter.ofPattern("yyyy.mm.dd")
            dateTextView.text = gift.giftReceiveDate.format(formatter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return PagerViewHolder(view)
    }
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(giftListResponse.giftListGifts[position])
    }

    override fun getItemCount(): Int = giftListResponse.giftListTotalCount
}
