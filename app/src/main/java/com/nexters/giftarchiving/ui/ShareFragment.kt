package com.nexters.giftarchiving.ui

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.kakao.sdk.link.LinkClient
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentShareBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast
import com.nexters.giftarchiving.util.ImageConverter
import com.nexters.giftarchiving.util.ImageManager
import com.nexters.giftarchiving.viewmodel.ShareViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ShareFragment : BaseFragment<ShareViewModel, FragmentShareBinding>() {
    override val layoutId = R.layout.fragment_share
    override val viewModel: ShareViewModel by viewModel()
    override val navArgs by navArgs<ShareFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sendArgToBackStack("isEdit", false)
        setLetterAnimation()
        observe(viewModel.shareKakaoMessage) { shareKakaoMessage() }
        observe(viewModel.saveImage) { if (checkWritePermission()) saveImage() }
    }

    private fun setLetterAnimation() {
        val transAnim = TranslateAnimation(
            ANIMATION_DEFAULT_DELTA,
            ANIMATION_DEFAULT_DELTA,
            ANIMATION_FROM_Y_DELTA,
            ANIMATION_DEFAULT_DELTA
        ).apply {
            startOffset = ANIMATION_START_OFFSET
            duration = ANIMATION_DURATION
            interpolator = BounceInterpolator()
        }
        binding.shareLetterPaperLayout.startAnimation(transAnim)
    }

    private fun checkWritePermission(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val readPermission = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            toast(NOTICE_DO_NOT_USE_SAVE_IMG)
            if (readPermission) {
                requestPermissions(permissions,
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE
                )
            }
            return false
        } else {
            return true
        }
    }

    private fun saveImage() {
        val bitmap = ImageConverter.layoutToBitmap(binding.saveImageLayout)
        ImageManager.saveImage(requireContext().contentResolver, bitmap)
        showSaveImageNotice()
    }

    private fun showSaveImageNotice() {
        binding.noticeSaveImageLayout.apply {
            visibility = View.VISIBLE
            alpha = 1.0f

            animate()
                .setStartDelay(1000L)
                .alpha(0f)
                .setDuration(500L)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        visibility = View.GONE
                    }
                }).start()
        }
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
        private const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 101

        private const val ANIMATION_DEFAULT_DELTA = 0f
        private const val ANIMATION_FROM_Y_DELTA = -150f
        private const val ANIMATION_START_OFFSET = 200L
        private const val ANIMATION_DURATION = 2500L

        private const val FAIL_SHARE_KAKAO_MESSAGE = "카카오 메세지 공유가 불가능합니다"
        private const val NOTICE_DO_NOT_USE_SAVE_IMG = "이미지 저장을 위해 접근권한이 필요합니다."
    }
}