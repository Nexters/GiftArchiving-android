package com.nexters.giftarchiving.util.theme

import androidx.annotation.ColorRes
import com.nexters.giftarchiving.R

enum class BackgroundColorTheme(@ColorRes val backgroundColor: Int, val isDarkMode: Boolean) {
    MONO(R.color.colorMono, true),
    BLUE(R.color.colorBlue, true),
    ORANGE(R.color.colorOrange, true),
    YELLOW(R.color.colorYellow, false)
}