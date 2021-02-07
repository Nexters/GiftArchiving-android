package com.nexters.giftarchiving.data.write

import androidx.annotation.DrawableRes
import com.nexters.giftarchiving.base.BaseItem

internal data class WriteStickerMenuItem(
    @DrawableRes val stickerResId: Int,
    override val itemId: String = stickerResId.toString()
) : BaseItem