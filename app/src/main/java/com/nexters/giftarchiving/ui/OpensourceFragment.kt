package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentOpensourceBinding
import com.nexters.giftarchiving.viewmodel.SettingsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class OpensourceFragment : BaseFragment<SettingsViewModel, FragmentOpensourceBinding>() {
    override val layoutId = R.layout.fragment_opensource
    override val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val osNames = arrayListOf<String>("Coroutine","Koin","Retrofit","Gson","Glide","Android Image Cropper","StickerView","CircleIndicator","KakaoSDK2-Android")
        val osDetails = arrayListOf<String>(getString(R.string.coroutine_opensource_detail),getString(R.string.koin_opensource_detail),getString(R.string.retrofit_opensource_detail)
        ,getString(R.string.gson_opensource_detail),getString(R.string.glide_opensource_detail),getString(R.string.cropper_opensource_detail),getString(R.string.stickerview_opensource_detail)
        ,getString(R.string.circleIndicator_opensource_detail),getString(R.string.kakaoSDK_opensource_detail))
        val osAdapter = OpensourceListAdapter(osNames,osDetails)
        binding.opensourceDetailText.adapter = osAdapter
        binding.opensourceDetailText.layoutManager =LinearLayoutManager(this.requireContext())
    }

    inner class OpensourceListAdapter(private val titles: ArrayList<String>, private val details: ArrayList<String>) : RecyclerView.Adapter<OpensourceListAdapter.PagerViewHolder>() {

        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val nameTextView: TextView = itemView.findViewById(R.id.recyclerView_opensource_name)
            private val detailTextView: TextView = itemView.findViewById(R.id.recyclerView_opensource_detail)

            fun bind(title: String, detail: String, position: Int) {
                nameTextView.text = title
                detailTextView.text = detail
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.opensource_recyclerview_item, parent, false)
            return PagerViewHolder(view)
        }
        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bind(titles[position], details[position], position)
        }

        override fun getItemCount(): Int = titles.size
    }
}