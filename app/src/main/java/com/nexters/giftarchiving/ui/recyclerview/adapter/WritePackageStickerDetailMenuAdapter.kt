package com.nexters.giftarchiving.ui.recyclerview.adapter

import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseListAdapter
import com.nexters.giftarchiving.data.write.WriteStickerList
import com.nexters.giftarchiving.data.write.WriteStickerMenuItem
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WritePackageStickerDetailMenuAdapter(
    private val viewModel: WriteViewModel,
    private val packageType: WritePackageSticker
) : BaseListAdapter<WriteStickerMenuItem>() {
    override fun getItemViewTypeByItemType(item: WriteStickerMenuItem) =
        R.layout.item_write_sticker_package_detail

    init {
        val list = when (packageType) {
            WritePackageSticker.HEART -> WriteStickerList.getPackageStickerType1List()
            WritePackageSticker.DIARY -> WriteStickerList.getPackageStickerType2List()
        }
        submitList(list)
        setViewModel(viewModel)
    }
}