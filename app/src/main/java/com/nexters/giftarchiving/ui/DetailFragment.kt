package com.nexters.giftarchiving.ui

import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentDetailBinding
import com.nexters.giftarchiving.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {
    override val layoutId = R.layout.fragment_detail
    override val viewModel: DetailViewModel by viewModel()
    override val navArgs by navArgs<DetailFragmentArgs>()
}