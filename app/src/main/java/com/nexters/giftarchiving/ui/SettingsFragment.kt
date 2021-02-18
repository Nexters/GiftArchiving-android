package com.nexters.giftarchiving.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentSettingsBinding
import com.nexters.giftarchiving.viewmodel.SettingsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {
    override val layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingQuestion.setOnClickListener {
            onClickEmail()
        }
    }

    fun onClickEmail(){
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.apply {
            type = "text/html"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("giftzip.team@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT,"문의 및 건의하기")
            putExtra(Intent.EXTRA_TEXT,"- 정확한 문의 파악을 위해 아래 정보를 작성해주세요!\n\n\n1. 문의 내용: \n\n2. 기프트집(카카오) 메일계정: \n\n★문의 관련 스크린샷을 첨부하시면\n보다 정확하고 빠른 확인이 가능합니다.")
        }
        startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }
}