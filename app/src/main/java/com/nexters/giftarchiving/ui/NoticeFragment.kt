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
import com.nexters.giftarchiving.databinding.FragmentNoticeBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.model.NoticeResponse
import com.nexters.giftarchiving.viewmodel.SettingsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class NoticeFragment : BaseFragment<SettingsViewModel, FragmentNoticeBinding>() {
    override val layoutId = R.layout.fragment_notice
    override val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.notices){
            if(it.size==0){
                binding.noneDataLayout.visibility = View.VISIBLE
            } else{
                binding.noneDataLayout.visibility = View.GONE
            }
            binding.noticeRecyclerView.adapter = NoticeListAdapter(it)
            binding.noticeRecyclerView.layoutManager = LinearLayoutManager(requireContext()).also { it.orientation = LinearLayoutManager.VERTICAL }
        }
    }

    inner class NoticeListAdapter(private val notices: List<NoticeResponse>) : RecyclerView.Adapter<NoticeListAdapter.PagerViewHolder>() {

        inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val title: TextView = itemView.findViewById(R.id.notice_item_title)
            private val content: TextView = itemView.findViewById(R.id.notice_item_content)
            private val date: TextView = itemView.findViewById(R.id.notice_item_date)

            fun bind(notice: NoticeResponse) {
                title.text = notice.title
                content.text = notice.content
                date.text = notice.createdAt
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.notice_recyclerview_item, parent, false)
            return PagerViewHolder(view)
        }
        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.bind(notices[position])
        }

        override fun getItemCount(): Int = notices.size

    }
}