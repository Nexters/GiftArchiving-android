package com.nexters.giftarchiving.ui.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.giftarchiving.ui.GivenFragment
import com.nexters.giftarchiving.ui.TakenFragment

class HomeViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_PAGES = 2
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0) {
            TakenFragment()
        }else {
            GivenFragment()
        }
    }
}