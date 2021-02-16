package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentLoginBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast
import com.nexters.giftarchiving.ui.viewpager.adapter.LoginGuideViewPagerAdapter
import com.nexters.giftarchiving.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
    override val layoutId = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.kakaoLogin) { kakaoLogin() }
        val viewPager = binding.loginGuideViewpager
        val indicator = binding.loginGuideIndicator
        indicator.createIndicators(3,0)
        viewPager.adapter = LoginGuideViewPagerAdapter(this)
        indicator.setViewPager(viewPager)
        viewPager.adapter?.registerAdapterDataObserver(indicator.adapterDataObserver)
    }

    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                toast(NOTICE_LOGIN_FAIL)
            } else if (token != null) {
                successLogin()
            }
        }

        if (LoginClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            LoginClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }
    }

    private fun successLogin() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, _ ->
            if (tokenInfo != null) {
                viewModel.signIn(userId = tokenInfo.id)
            }
        }
    }

    companion object {
        private const val NOTICE_LOGIN_FAIL = "로그인 실패"
    }
}