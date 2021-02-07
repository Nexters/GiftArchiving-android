package com.nexters.giftarchiving.ui.data.write

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.nexters.giftarchiving.R

enum class WriteStickerTabLayoutTheme(
    @ColorRes val tabTextColor: Int,
    @ColorRes val tabSelectedTextColor: Int
) {
    DARK_MODE(
        R.color.colorWriteStickerLightModeDefaultTextColor,
        R.color.white
    ),
    LIGHT_MODE(
        R.color.colorWriteStickerDarkModeDefaultTextColor,
        R.color.colorDarkGray
    )
}