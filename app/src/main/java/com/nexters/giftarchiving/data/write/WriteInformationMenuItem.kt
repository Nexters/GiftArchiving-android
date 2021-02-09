package com.nexters.giftarchiving.data.write

import com.nexters.giftarchiving.base.BaseItem
import com.nexters.giftarchiving.data.write.WriteInformationMenu
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme

internal data class WriteInformationMenuItem(
    val menu: WriteInformationMenu,
    val theme: BackgroundColorTheme,
    override val itemId: String = menu.title
) : BaseItem