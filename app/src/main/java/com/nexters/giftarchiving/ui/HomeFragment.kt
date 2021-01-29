package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentHomeBinding
import com.nexters.giftarchiving.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val layoutId = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.homeTabLayout
        val viewPager = binding.homeViewpager
        val tabTextList = arrayListOf(getString(R.string.home_taken_button_name),getString(R.string.home_given_button_name))
        viewPager.isUserInputEnabled = false
        viewPager.adapter = HomeViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = String.format(tabTextList[position])
        }.attach()
    }
}