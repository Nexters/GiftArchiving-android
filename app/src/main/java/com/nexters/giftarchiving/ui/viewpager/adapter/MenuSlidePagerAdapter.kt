package com.nexters.giftarchiving.ui.viewpager.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.giftarchiving.ui.WriteInformationMenuFragment
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class MenuSlidePagerAdapter(
    fa: FragmentActivity,
    private val viewModel: WriteViewModel,
    private val menuType: WriteMenu,
    private val itemCount: Int
) : FragmentStateAdapter(fa) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = WriteInformationMenuFragment(viewModel, menuType)
}