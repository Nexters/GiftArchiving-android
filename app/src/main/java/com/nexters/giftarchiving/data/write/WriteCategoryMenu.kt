package com.nexters.giftarchiving.data.write

import androidx.annotation.DrawableRes
import com.nexters.giftarchiving.R

internal data class WriteCategoryMenu(
    override val title: String = "카테고리",
    @DrawableRes override val darkIconRes: Int = R.drawable.ic_gift_card_b,
    @DrawableRes override val lightIconRes: Int = R.drawable.ic_gift_card
) : WriteInformationMenu