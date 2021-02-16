package com.nexters.giftarchiving.data.write

import androidx.annotation.DrawableRes
import com.nexters.giftarchiving.R

internal data class WriteCategoryMenu(
    override val title: String = "카테고리",
    override val titleEng: String? = null,
    @DrawableRes override val darkIconRes: Int = R.drawable.ic_icon_category_default_bk,
    @DrawableRes override val lightIconRes: Int = R.drawable.ic_icon_category_default
) : WriteInformationMenu