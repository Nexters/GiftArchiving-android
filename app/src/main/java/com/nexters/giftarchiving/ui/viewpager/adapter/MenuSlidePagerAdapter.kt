package com.nexters.giftarchiving.ui.viewpager.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.giftarchiving.data.write.WriteInformationMenuList
import com.nexters.giftarchiving.ui.WriteInformationMenuFragment
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class MenuSlidePagerAdapter(
    fa: FragmentActivity,
    private val viewModel: WriteViewModel,
    private val menuType: WriteMenu,
    private val isReceive: Boolean
) : FragmentStateAdapter(fa) {
    override fun getItemCount() = when (menuType) {
        WriteMenu.INFORMATION_PURPOSE -> WriteInformationMenuList.purposeMenuList
        WriteMenu.INFORMATION_CATEGORY -> WriteInformationMenuList.categoryMenuList
        WriteMenu.INFORMATION_EMOTION -> {
            if (isReceive) WriteInformationMenuList.receiveEmotionList
            else WriteInformationMenuList.sendEmotionList
        }
        else -> null
    }?.size?.div(WriteViewModel.INFORMATION_NUMBER_OF_PAGE)?.plus(1) ?: 0

    override fun createFragment(position: Int) =
        WriteInformationMenuFragment(viewModel, menuType, isReceive, position)
}