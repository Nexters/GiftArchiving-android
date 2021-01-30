package com.nexters.giftarchiving.ui

import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentShareBinding
import com.nexters.giftarchiving.viewmodel.ShareViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ShareFragment : BaseFragment<ShareViewModel, FragmentShareBinding>() {
    override val layoutId = R.layout.fragment_share
    override val viewModel: ShareViewModel by viewModel()
    override val navArgs by navArgs<ShareFragmentArgs>()
}