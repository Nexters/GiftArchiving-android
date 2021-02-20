package com.nexters.giftarchiving.ui

import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewPagerFragment
import com.nexters.giftarchiving.databinding.FragmentWriteStickerPackageSingleBinding
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker
import com.nexters.giftarchiving.ui.recyclerview.adapter.WritePackageStickerSingleMenuAdapter
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteStickerPackageSingleFragment(
    override val viewModel: WriteViewModel,
    private val packageType: WritePackageSticker
) : BaseViewPagerFragment<WriteViewModel, FragmentWriteStickerPackageSingleBinding>() {
    override val layoutId = R.layout.fragment_write_sticker_package_single

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding.stickerRv) {
            adapter = WritePackageStickerSingleMenuAdapter(viewModel, packageType)
        }
    }
}