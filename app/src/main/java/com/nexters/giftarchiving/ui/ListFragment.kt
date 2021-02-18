package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentListBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.ui.viewpager.adapter.ListViewPagerAdapter
import com.nexters.giftarchiving.viewmodel.ListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>() {
    override val layoutId = R.layout.fragment_list
    override val viewModel: ListViewModel by viewModel()
    override val navArgs: ListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listTypeViewPager = binding.listViewPager
        val switchButton = binding.listStyleButton
        listTypeViewPager.isUserInputEnabled = false
        observe(viewModel.isLatest){ isLatest ->
            if(viewModel.isReceived){
                observe(viewModel.getAllReceivedGiftListResponse){
                    if(isLatest){
                        val listViewPagerAdapter = ListViewPagerAdapter(this,it.giftListGifts,viewModel,viewModel.isReceived)
                        listTypeViewPager.adapter = listViewPagerAdapter
                    } else{
                        val temp = it.giftListGifts.reversed()
                        val listViewPagerAdapter = ListViewPagerAdapter(this,temp,viewModel,viewModel.isReceived)
                        listTypeViewPager.adapter = listViewPagerAdapter
                    }
                }
            } else{
                observe(viewModel.getAllNotReceivedGiftListResponse){
                    if(isLatest){
                        val listViewPagerAdapter = ListViewPagerAdapter(this,it.giftListGifts,viewModel,viewModel.isReceived)
                        listTypeViewPager.adapter = listViewPagerAdapter
                    } else{
                        val temp = it.giftListGifts.reversed()
                        val listViewPagerAdapter = ListViewPagerAdapter(this,temp,viewModel,viewModel.isReceived)
                        listTypeViewPager.adapter = listViewPagerAdapter
                    }
                }
            }
            if (isLatest) {
                binding.sortLatestCheck.visibility = View.VISIBLE
                binding.sortPastCheck.visibility = View.GONE
            } else {
                binding.sortLatestCheck.visibility = View.GONE
                binding.sortPastCheck.visibility = View.VISIBLE
            }
            observe(viewModel.listType) {
                if (it) {
                    listTypeViewPager.currentItem = 0
                    switchButton.setImageResource(R.drawable.ic_icon_2_grid)
                } else {
                    listTypeViewPager.currentItem = 1
                    switchButton.setImageResource(R.drawable.ic_icon_1_grid)
                }
            }
        }
        listTypeViewPager.setPageTransformer(ViewPager2.PageTransformer { page, position ->
            page.apply {
                val pageWidth = width
                when {
                    position < -0.5f -> {
                        alpha = 0f
                        translationX = pageWidth * -position
                        translationZ = -1f
                    }
                    position <= 0.5f -> {
                        translationX = pageWidth * -position
                        alpha = 1f
                        translationZ = 0f
                    }
                    else -> {
                        translationX = pageWidth * position
                        alpha = 0f
                    }
                }
            }
        })
        observe(viewModel.listType) {
            if (it) {
                listTypeViewPager.currentItem = 0
                switchButton.setImageResource(R.drawable.ic_icon_2_grid)
            } else {
                listTypeViewPager.currentItem = 1
                switchButton.setImageResource(R.drawable.ic_icon_1_grid)
            }
        }
        observe(viewModel.showSortBottom) {
            if (it) {
                showSortBottom()
            } else {
                hideSortBottom()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllList()
    }

    private fun showSortBottom(){
        binding.sortLayout.visibility = View.VISIBLE
    }

    private fun hideSortBottom() {
        binding.sortLayout.visibility = View.GONE
    }
}