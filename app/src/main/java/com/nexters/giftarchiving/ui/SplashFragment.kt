package com.nexters.giftarchiving.ui

import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentSplashBinding
import com.nexters.giftarchiving.util.KakaoParamKey
import com.nexters.giftarchiving.viewmodel.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {
    override val layoutId = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().intent.data?.getQueryParameter(KakaoParamKey.GIFT_ID)?.let {
            viewModel.navDirections.value =
                SplashFragmentDirections.actionSplashFragmentToDetailFragment(it)
        }
    }
}