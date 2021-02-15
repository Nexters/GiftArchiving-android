package com.nexters.giftarchiving.data.write

import androidx.annotation.DrawableRes
import com.nexters.giftarchiving.R

internal data class WriteEmotionMenu(
    override val title: String = "감정",
    @DrawableRes override val darkIconRes: Int = R.drawable.ic_icon_feeling_default_bk,
    @DrawableRes override val lightIconRes: Int = R.drawable.ic_icon_feeling_default
) : WriteInformationMenu