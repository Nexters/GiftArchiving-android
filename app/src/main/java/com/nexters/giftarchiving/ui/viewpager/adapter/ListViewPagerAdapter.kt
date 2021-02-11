package com.nexters.giftarchiving.ui.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.giftarchiving.ui.ListType1Fragment
import com.nexters.giftarchiving.ui.ListType2Fragment

class ListViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_PAGES = 2
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0) {
            ListType1Fragment()
        }else {
            ListType2Fragment()
        }
    }
}