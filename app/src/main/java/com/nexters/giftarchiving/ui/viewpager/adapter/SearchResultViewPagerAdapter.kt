package com.nexters.giftarchiving.ui.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.giftarchiving.model.GiftResponse
import com.nexters.giftarchiving.ui.SearchResultRecyclerFragment
import com.nexters.giftarchiving.viewmodel.SearchViewModel

internal class SearchResultViewPagerAdapter(fragment: Fragment, val takenList : ArrayList<GiftResponse>, val givenList : ArrayList<GiftResponse>, val viewModel : SearchViewModel) : FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_PAGES = 2
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0) {
            SearchResultRecyclerFragment(takenList,viewModel,true)
        }else {
            SearchResultRecyclerFragment(givenList,viewModel,false)
        }
    }
}