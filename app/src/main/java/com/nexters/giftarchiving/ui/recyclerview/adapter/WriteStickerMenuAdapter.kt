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
        submitList(
            listOf(
                WriteStickerMenuItem(R.drawable.ic_sticker_22),
                WriteStickerMenuItem(R.drawable.ic_sticker_23),
                WriteStickerMenuItem(R.drawable.ic_sticker_24),
                WriteStickerMenuItem(R.drawable.ic_sticker_25),
                WriteStickerMenuItem(R.drawable.ic_sticker_26),
                WriteStickerMenuItem(R.drawable.ic_sticker_27),
                WriteStickerMenuItem(R.drawable.ic_sticker_28),
                WriteStickerMenuItem(R.drawable.ic_sticker_29),
                WriteStickerMenuItem(R.drawable.ic_sticker_30),
                WriteStickerMenuItem(R.drawable.ic_sticker_31),
                WriteStickerMenuItem(R.drawable.ic_sticker_32),
                WriteStickerMenuItem(R.drawable.ic_sticker_33),
                WriteStickerMenuItem(R.drawable.ic_sticker_34),
                WriteStickerMenuItem(R.drawable.ic_sticker_35),
                WriteStickerMenuItem(R.drawable.ic_sticker_36),
                WriteStickerMenuItem(R.drawable.ic_sticker_37),
                WriteStickerMenuItem(R.drawable.sticker_1),
                WriteStickerMenuItem(R.drawable.sticker_2),
                WriteStickerMenuItem(R.drawable.sticker_3),
                WriteStickerMenuItem(R.drawable.sticker_4),
                WriteStickerMenuItem(R.drawable.sticker_5),
                WriteStickerMenuItem(R.drawable.sticker_6),
                WriteStickerMenuItem(R.drawable.sticker_7),
                WriteStickerMenuItem(R.drawable.sticker_8),
                WriteStickerMenuItem(R.drawable.sticker_9),
                WriteStickerMenuItem(R.drawable.sticker_10),
                WriteStickerMenuItem(R.drawable.sticker_11),
                WriteStickerMenuItem(R.drawable.sticker_12),
                WriteStickerMenuItem(R.drawable.sticker_13),
                WriteStickerMenuItem(R.drawable.sticker_14),
                WriteStickerMenuItem(R.drawable.sticker_15),
                WriteStickerMenuItem(R.drawable.sticker_16),
                WriteStickerMenuItem(R.drawable.sticker_17),
                WriteStickerMenuItem(R.drawable.sticker_18),
                WriteStickerMenuItem(R.drawable.sticker_19),
                WriteStickerMenuItem(R.drawable.sticker_20),
                WriteStickerMenuItem(R.drawable.sticker_21)
            )
        )
        setViewModel(viewModel)
    }
}