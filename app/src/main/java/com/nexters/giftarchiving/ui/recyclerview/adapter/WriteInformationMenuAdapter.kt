package com.nexters.giftarchiving.ui.recyclerview.adapter

import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseListAdapter
import com.nexters.giftarchiving.data.write.WriteInformationMenuItem
import com.nexters.giftarchiving.data.write.WriteInformationMenuList
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteInformationMenuAdapter(
    private val viewModel: WriteViewModel,
    private val menuType: WriteMenu,
    private val isReceive: Boolean,
    private val position: Int
) : BaseListAdapter<WriteInformationMenuItem>() {
    override fun getItemViewTypeByItemType(item: WriteInformationMenuItem) =
        R.layout.item_write_information

    init {
        setList()
        setViewModel(viewModel)
    }

    private fun setList() {
        val list = when (menuType) {
            WriteMenu.INFORMATION_CATEGORY -> WriteInformationMenuList.categoryMenuList
            WriteMenu.INFORMATION_PURPOSE -> WriteInformationMenuList.purposeMenuList
            WriteMenu.INFORMATION_EMOTION -> {
                if (isReceive) WriteInformationMenuList.receiveEmotionList
                else WriteInformationMenuList.sendEmotionList
            }
            else -> null
        }?.let {
            val num = WriteViewModel.INFORMATION_NUMBER_OF_PAGE
            val from = position.times(num)
            val to = from.plus(num).coerceAtMost(it.size)
            it.subList(from, to)
        }
        submitList(list)
    }
}