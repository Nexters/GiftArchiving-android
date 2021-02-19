package com.nexters.giftarchiving.ui.recyclerview.adapter

import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseListAdapter
import com.nexters.giftarchiving.data.write.WriteStickerList
import com.nexters.giftarchiving.data.write.WriteStickerMenuItem
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteStickerMenuAdapter(
    private val viewModel: WriteViewModel
) : BaseListAdapter<WriteStickerMenuItem>() {
    override fun getItemViewTypeByItemType(item: WriteStickerMenuItem) =
        R.layout.item_write_sticker

    init {
        submitList(WriteStickerList.getSingleStickerList())
        setViewModel(viewModel)
    }
}