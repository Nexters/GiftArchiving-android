package com.nexters.giftarchiving.ui

import android.content.Intent
import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentAboutUsBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.viewmodel.AboutUsViewModel
import org.koin.android.viewmodel.ext.android.viewModel


internal class AboutUsFragment : BaseFragment<AboutUsViewModel, FragmentAboutUsBinding>() {
    override val layoutId = R.layout.fragment_about_us
    override val viewModel: AboutUsViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.clickMail) { sentEmail() }
    }

    private fun sentEmail() {
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
    }
}