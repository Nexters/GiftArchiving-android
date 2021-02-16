package com.nexters.giftarchiving.data.write

import androidx.annotation.DrawableRes
import com.nexters.giftarchiving.R

internal data class WritePurposeMenu(
    override val title: String = "목적",
    override val titleEng: String? = null,
    @DrawableRes override val darkIconRes: Int = R.drawable.ic_icon_purpose_default_bk,
    @DrawableRes override val lightIconRes: Int = R.drawable.ic_icon_purpose_default
) : WriteInformationMenu