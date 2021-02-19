package com.nexters.giftarchiving.data.write

import androidx.annotation.DrawableRes
import com.nexters.giftarchiving.base.BaseItem
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker

internal class WritePackageStickerMenuItem(
    @DrawableRes val stickerResId: Int? = null,
    val packageType: WritePackageSticker = WritePackageSticker.HEART,
    override val itemId: String = stickerResId.toString()
): BaseItem