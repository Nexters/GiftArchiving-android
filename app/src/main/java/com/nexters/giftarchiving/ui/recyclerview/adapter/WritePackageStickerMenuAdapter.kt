package com.nexters.giftarchiving.ui.recyclerview.adapter

import androidx.databinding.ViewDataBinding
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseListAdapter
import com.nexters.giftarchiving.base.BaseViewHolder
import com.nexters.giftarchiving.data.write.WritePackageStickerMenuItem
import com.nexters.giftarchiving.data.write.WriteStickerList
import com.nexters.giftarchiving.databinding.ItemWriteStickerPackageBinding
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker
import com.nexters.giftarchiving.ui.recyclerview.viewholder.WritePackageStickerMenuViewHolder
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WritePackageStickerMenuAdapter(
    private val viewModel: WriteViewModel,
    private val onClickCallBack: (WritePackageSticker) -> Unit = {}
) : BaseListAdapter<WritePackageStickerMenuItem>() {
    override fun getItemViewTypeByItemType(item: WritePackageStickerMenuItem) =
        R.layout.item_write_sticker_package

    init {
        submitList(WriteStickerList.getPackageStickers())
        setViewModel(viewModel)
    }

    override fun createViewHolder(
        binding: ViewDataBinding,
        viewType: Int
    ): BaseViewHolder<WritePackageStickerMenuItem> {
        val packageBinding = binding as ItemWriteStickerPackageBinding
        return WritePackageStickerMenuViewHolder(packageBinding, onClickCallBack)
    }
}