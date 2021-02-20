package com.nexters.giftarchiving.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseConfirmDialogListener
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentSettingsBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.viewmodel.SettingsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {
    override val layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.clickLogout) { showLogoutConfirmDialog() }
        binding.settingQuestion.setOnClickListener {
            onClickEmail()
        }
    }

    private fun showLogoutConfirmDialog() {
        val listener = object : BaseConfirmDialogListener() {
            override fun onConfirm() {
                super.onConfirm()
                viewModel.logout()
            }
        }
        ConfirmBottomSheet(
            title = "로그아웃 하시겠어요?",
            subTitle = "선물 등록을 할 수 없어요.",
            confirmTitle = "로그아웃",
            listener = listener
        ).show(parentFragmentManager, LOGOUT_DIALOG_TAG)
    }

    private fun onClickEmail() {
        val emails = arrayOf(getString(R.string.gift_zip_email))
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = INTENT_EMAIL_TYPE
            putExtra(Intent.EXTRA_EMAIL, emails)
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.qna_title))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.qna_content))
        }
        try {
            emailIntent.`package` = INTENT_GMAIL_PACKAGE
            startActivity(emailIntent)
        } catch (e: Exception) {
            startActivity(Intent.createChooser(emailIntent, INTENT_EMAIL_TITLE))
        }
    }

    companion object {
        private const val INTENT_EMAIL_TITLE = "Send Email"
        private const val INTENT_EMAIL_TYPE = "text/html"
        private const val INTENT_GMAIL_PACKAGE = "com.google.android.gm"

        private const val LOGOUT_DIALOG_TAG = "logout dialog"
    }
}