package com.nexters.giftarchiving.ui

import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewPagerFragment
import com.nexters.giftarchiving.databinding.FragmentWriteStickerPackageDetailBinding
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker
import com.nexters.giftarchiving.ui.recyclerview.adapter.WritePackageStickerDetailMenuAdapter
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteStickerPackageDetailFragment(
    override val viewModel: WriteViewModel,
    private val packageType: WritePackageSticker
) : BaseViewPagerFragment<WriteViewModel, FragmentWriteStickerPackageDetailBinding>() {
    override val layoutId = R.layout.fragment_write_sticker_package_detail

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding.stickerRv) {
            adapter = WritePackageStickerDetailMenuAdapter(viewModel, packageType)
        }
    }
}