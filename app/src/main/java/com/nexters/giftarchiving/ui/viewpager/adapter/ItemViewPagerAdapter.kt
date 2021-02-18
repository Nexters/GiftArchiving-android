package com.nexters.giftarchiving.ui.viewpager.adapter

import android.content.Context
import android.icu.text.SimpleDateFormat
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
import com.nexters.giftarchiving.model.GiftResponse
import com.nexters.giftarchiving.viewmodel.ListViewModel
import java.time.format.DateTimeFormatter
import java.util.*

internal class ItemViewPagerAdapter(
    val context: Context,
    private val giftListResponse: List<GiftResponse>,
    private val viewType: Int,
    private val viewModel: ListViewModel?
) : RecyclerView.Adapter<ItemViewPagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemImageView: ImageView = itemView.findViewById(R.id.item_image)
        private val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.item_constraintLayout)
        private val personTextView: TextView = itemView.findViewById(R.id.item_person)
        private val dateTextView: TextView = itemView.findViewById(R.id.item_date)

        fun bind(gift : GiftResponse) {
            if(gift.giftId=="empty"){
                val defaultText : TextView = itemView.findViewById(R.id.item_default_text)
                defaultText.text = gift.giftContent
                personTextView.text = gift.giftName
            } else{
                Glide.with(context).load(gift.giftImgUrl).into(itemImageView)
                if(viewType==2||viewType==3){
                    constraintLayout.clipToOutline = true
                    when(gift.bgColor){
                        "ORANGE" -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_orange_background)
                        "BLUE" -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_blue_background)
                        "YELLOW" -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context, R.drawable.round_yellow_background)
                        else -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_gray_background)
                    }
                }
                if (gift.bgColor=="YELLOW"){
                    personTextView.setTextColor(ContextCompat.getColor(context,R.color.black))
                    dateTextView.setTextColor(ContextCompat.getColor(context,R.color.black))
                }
                if(viewType==0||viewType==2){
                    personTextView.text = String.format("From. %s",gift.giftName)
                } else{
                    personTextView.text = String.format("To. %s",gift.giftName)
                }
                var inputDate = String.format("%s.%s.%s",gift.giftReceiveDate.substring(0,4),gift.giftReceiveDate.substring(5,7),gift.giftReceiveDate.substring(8,10))
                val dateFormat = SimpleDateFormat("yyyy.MM.dd")
                val date = dateFormat.parse(inputDate)
                val calendar = Calendar.getInstance()
                calendar.time = date
                when(calendar.get(Calendar.DAY_OF_WEEK)){
                    1 -> inputDate += " (일)"
                    2 -> inputDate += " (월)"
                    3 -> inputDate += " (화)"
                    4 -> inputDate += " (수)"
                    5 -> inputDate += " (목)"
                    6 -> inputDate += " (금)"
                    else -> inputDate += " (토)"
                }
                dateTextView.text = inputDate
                viewModel?.let { vm -> itemView.setOnClickListener { vm.onClickDetail(gift.giftId) } }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return PagerViewHolder(view)
    }
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(giftListResponse[position])
    }

    override fun getItemCount(): Int = giftListResponse.size
}
