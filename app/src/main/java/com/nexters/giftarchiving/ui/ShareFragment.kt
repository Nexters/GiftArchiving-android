package com.nexters.giftarchiving.ui

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.kakao.sdk.link.LinkClient
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentShareBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast
import com.nexters.giftarchiving.viewmodel.ShareViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ShareFragment : BaseFragment<ShareViewModel, FragmentShareBinding>() {
    override val layoutId = R.layout.fragment_share
    override val viewModel: ShareViewModel by viewModel()
    override val navArgs by navArgs<ShareFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe(viewModel.shareKakaoMessage) { shareKakaoMessage() }
    }

    private fun shareKakaoMessage() {
        val feed = viewModel.getKakaoMessageFeed()
        if (feed != null) {
            LinkClient.instance.defaultTemplate(requireContext(), feed) { linkResult, error ->
                if (error != null) {
                    toast(FAIL_SHARE_KAKAO_MESSAGE)
                } else if (linkResult != null) {
                    startActivity(linkResult.intent)
                }
            }
        } else {
            toast(FAIL_SHARE_KAKAO_MESSAGE)
        }
    }

    companion object {
        private const val FAIL_SHARE_KAKAO_MESSAGE = "카카오 메세지 공유가 불가능합니다"
    }
}