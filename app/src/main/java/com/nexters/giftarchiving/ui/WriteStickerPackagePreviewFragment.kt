package com.nexters.giftarchiving.ui

import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewPagerFragment
import com.nexters.giftarchiving.databinding.FragmentWriteStickerPackagePreviewBinding
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker
import com.nexters.giftarchiving.ui.data.write.WriteSticker
import com.nexters.giftarchiving.ui.recyclerview.adapter.WritePackageStickerMenuAdapter
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteStickerPackagePreviewFragment(
    override val viewModel: WriteViewModel,
    private val onClickCallBack: (WriteSticker, WritePackageSticker) -> Unit
) : BaseViewPagerFragment<WriteViewModel, FragmentWriteStickerPackagePreviewBinding>() {
    override val layoutId = R.layout.fragment_write_sticker_package_preview

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding.stickerRv) {
            adapter = WritePackageStickerMenuAdapter(viewModel) {
                onClickCallBack(WriteSticker.SINGLE, it)
            }
        }
    }
}