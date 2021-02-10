package com.nexters.giftarchiving.ui.recyclerview.adapter

import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseListAdapter
import com.nexters.giftarchiving.data.write.WriteInformationMenuItem
import com.nexters.giftarchiving.data.write.WriteInformationMenuList
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteInformationMenuAdapter(
    private val viewModel: WriteViewModel,
    private val menuType: WriteMenu
) : BaseListAdapter<WriteInformationMenuItem>() {
    override fun getItemViewTypeByItemType(item: WriteInformationMenuItem) =
        R.layout.item_write_information

    init {
        val list = when(menuType) {
            WriteMenu.INFORMATION_CATEGORY -> WriteInformationMenuList.categoryMenuList
            WriteMenu.INFORMATION_PURPOSE -> WriteInformationMenuList.purposeMenuList
            WriteMenu.INFORMATION_EMOTION -> WriteInformationMenuList.sendEmotionList
            else -> null
        }
        submitList(list)
        setViewModel(viewModel)
    }
}