package com.nexters.giftarchiving.util.theme

import androidx.annotation.ColorRes
import com.nexters.giftarchiving.R

enum class BackgroundColorTheme(
    @ColorRes val backgroundColor: Int,
    @ColorRes val popupBackgroundColor: Int,
    val isDarkMode: Boolean
) {
    MONO(R.color.colorMono, R.color.colorPopupMono, true),
    BLUE(R.color.colorBlue, R.color.colorPopupBlue, true),
    ORANGE(R.color.colorOrange, R.color.colorPopupOrange, true),
    YELLOW(R.color.colorYellow, R.color.colorPopupYellow, false)
}