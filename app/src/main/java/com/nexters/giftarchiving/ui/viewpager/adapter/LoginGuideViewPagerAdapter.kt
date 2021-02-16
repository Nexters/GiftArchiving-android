package com.nexters.giftarchiving.ui.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.giftarchiving.ui.*

class LoginGuideViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_PAGES = 3
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->Guide1Fragment()
            1->Guide2Fragment()
            else-> Guide3Fragment()
        }
    }
}