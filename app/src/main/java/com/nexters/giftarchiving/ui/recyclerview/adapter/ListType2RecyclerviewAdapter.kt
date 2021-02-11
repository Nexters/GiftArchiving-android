package com.nexters.giftarchiving.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nexters.giftarchiving.R

class ListType2RecyclerviewAdapter(private val context: Context, private val bgColors: ArrayList<Int>, private val people: ArrayList<String>, private val dates:ArrayList<String>) : RecyclerView.Adapter<ListType2RecyclerviewAdapter.ItemViewHolder>() {

    var mPosition = 0

    fun getPosition() : Int{
        return mPosition
    }

    private fun setPosition(position: Int){
        mPosition = position
    }

    fun addItem(bgColor: Int, person: String, date : String){
        bgColors.add(bgColor)
        people.add(person)
        dates.add(date)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemImageView: ImageView = itemView.findViewById(R.id.grid_image)
        private val personTextView: TextView = itemView.findViewById(R.id.grid_person)
        private val dateTextView: TextView = itemView.findViewById(R.id.grid_date)

        fun bind(bgColor: Int, person: String, date: String, position: Int) {
            when(bgColor){
                R.color.orange -> itemImageView.background = ContextCompat.getDrawable(itemImageView.context,R.drawable.round_orange_background)
                R.color.blue -> itemImageView.background = ContextCompat.getDrawable(itemImageView.context,R.drawable.round_blue_background)
                R.color.yellow -> itemImageView.background = ContextCompat.getDrawable(itemImageView.context,R.drawable.round_yellow_background)
                else -> itemImageView.background = ContextCompat.getDrawable(itemImageView.context,R.drawable.round_gray_background)
            }
            itemImageView.clipToOutline = true
            personTextView.text = person
            dateTextView.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_layout, parent, false)
        return ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(bgColors[position], people[position], dates[position], position)
    }

    override fun getItemCount(): Int = bgColors.size
}
