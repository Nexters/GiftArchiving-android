package com.nexters.giftarchiving.ui.recyclerview.adapter

import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseListAdapter
import com.nexters.giftarchiving.data.write.WriteInformationMenu
import com.nexters.giftarchiving.data.write.WriteInformationMenuItem
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteInformationMenuAdapter(
    private val viewModel: WriteViewModel
) : BaseListAdapter<WriteInformationMenuItem>() {
    override fun getItemViewTypeByItemType(item: WriteInformationMenuItem) =
        R.layout.item_write_information

    init {
        val menu =
            WriteInformationMenu(
                "생일선물",
                R.drawable.ic_icon_present_b,
                R.drawable.ic_icon_present
            )
        val theme = viewModel.backgroundColorTheme.value ?: BackgroundColorTheme.MONO
        submitList(
            listOf(
                WriteInformationMenuItem(
                    menu,
                    theme
                ),
                WriteInformationMenuItem(
                    menu,
                    theme
                ),
                WriteInformationMenuItem(
                    menu,
                    theme
                ),
                WriteInformationMenuItem(
                    menu,
                    theme
                ),
                WriteInformationMenuItem(
                    menu,
                    theme
                ),
                WriteInformationMenuItem(
                    menu,
                    theme
                ),
                WriteInformationMenuItem(
                    menu,
                    theme
                ),
                WriteInformationMenuItem(
                    menu,
                    theme
                )
            )
        )
        setViewModel(viewModel)
    }
}