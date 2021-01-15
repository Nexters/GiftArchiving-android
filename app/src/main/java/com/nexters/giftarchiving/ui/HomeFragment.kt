package com.nexters.giftarchiving.ui

import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentHomeBinding
import com.nexters.giftarchiving.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val layoutId = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()
}