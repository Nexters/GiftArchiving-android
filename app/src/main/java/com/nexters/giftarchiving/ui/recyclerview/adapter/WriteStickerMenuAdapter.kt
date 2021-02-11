package com.nexters.giftarchiving.ui.recyclerview.adapter

import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseListAdapter
import com.nexters.giftarchiving.data.write.WriteStickerMenuItem
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteStickerMenuAdapter(
    private val viewModel: WriteViewModel,
    private val position: Int
) : BaseListAdapter<WriteStickerMenuItem>() {
    override fun getItemViewTypeByItemType(item: WriteStickerMenuItem) =
        R.layout.item_write_sticker

    init {
        val stickerId = R.drawable.ic_launcher_foreground
        submitList(
            listOf(
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId),
                WriteStickerMenuItem(stickerId)
            )
        )
        setViewModel(viewModel)
    }
}