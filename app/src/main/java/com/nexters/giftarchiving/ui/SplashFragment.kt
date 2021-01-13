package com.nexters.giftarchiving.ui

import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentSplashBinding
import com.nexters.giftarchiving.viewmodel.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {
    override val layoutId = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()
}