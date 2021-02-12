package com.nexters.giftarchiving.ui

import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentShareInstagramBinding
import com.nexters.giftarchiving.viewmodel.ShareInstagramViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ShareInstagramFragment : BaseFragment<ShareInstagramViewModel, FragmentShareInstagramBinding>() {
    override val layoutId = R.layout.fragment_share_instagram
    override val viewModel: ShareInstagramViewModel by viewModel()
    override val navArgs by navArgs<ShareInstagramFragmentArgs>()
}