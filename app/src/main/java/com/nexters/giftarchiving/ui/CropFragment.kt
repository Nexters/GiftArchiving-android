package com.nexters.giftarchiving.ui

import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentCropBinding
import com.nexters.giftarchiving.viewmodel.CropViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class CropFragment : BaseFragment<CropViewModel, FragmentCropBinding>() {
    override val layoutId = R.layout.fragment_crop
    override val viewModel: CropViewModel by viewModel()
    override val navArgs by navArgs<CropFragmentArgs>()
}