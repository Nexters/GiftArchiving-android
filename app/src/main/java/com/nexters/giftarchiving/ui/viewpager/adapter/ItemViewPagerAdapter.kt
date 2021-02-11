package com.nexters.giftarchiving.ui.viewpager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nexters.giftarchiving.R

class ItemViewPagerAdapter(private val bgColors: ArrayList<Int>, private val people: ArrayList<String>, private val dates:ArrayList<String>, private val viewType: Int) : RecyclerView.Adapter<ItemViewPagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemImageView: ImageView = itemView.findViewById(R.id.item_image)
        private val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.item_constraintLayout)
        private val personTextView: TextView = itemView.findViewById(R.id.item_person)
        private val dateTextView: TextView = itemView.findViewById(R.id.item_date)

        fun bind(bgColor: Int, person: String, date: String, position: Int) {
            if(viewType==1){
                constraintLayout.clipToOutline = true
                when(bgColor){
                    R.color.orange -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_orange_background)
                    R.color.blue -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_blue_background)
                    R.color.yellow -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_yellow_background)
                    else -> constraintLayout.background = ContextCompat.getDrawable(constraintLayout.context,R.drawable.round_gray_background)
                }
            }
            personTextView.text = person
            dateTextView.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return PagerViewHolder(view)
    }
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(bgColors[position], people[position], dates[position], position)
    }

    override fun getItemCount(): Int = bgColors.size
}
