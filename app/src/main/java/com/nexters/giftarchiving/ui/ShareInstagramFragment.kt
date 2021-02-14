package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentShareInstagramBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.service.share.InstagramSharedService
import com.nexters.giftarchiving.util.ImageConverter
import com.nexters.giftarchiving.util.ImageManager
import com.nexters.giftarchiving.viewmodel.ShareInstagramViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ShareInstagramFragment :
    BaseFragment<ShareInstagramViewModel, FragmentShareInstagramBinding>() {
    override val layoutId = R.layout.fragment_share_instagram
    override val viewModel: ShareInstagramViewModel by viewModel()
    override val navArgs by navArgs<ShareInstagramFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.instagramStory) { instagramStory() }
        observe(viewModel.instagramFeed) { instagramFeed() }
    }

    private fun instagramStory() {
        val bitmap = ImageConverter.layoutToBitmap(binding.shareImageLayout)
        ImageManager.saveImage(requireContext().contentResolver, bitmap)?.let {
            InstagramSharedService.shareInstagramStory(requireActivity(), it)
        }
    }

    private fun instagramFeed() {
        val bitmap = ImageConverter.layoutToBitmap(binding.shareImageLayout)
        ImageManager.saveImage(requireContext().contentResolver, bitmap)?.let {
            InstagramSharedService.shareInstagramFeed(requireActivity(), it)
        }
    }
}