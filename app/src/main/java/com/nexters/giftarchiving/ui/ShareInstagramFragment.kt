package com.nexters.giftarchiving.ui

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentShareInstagramBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast
import com.nexters.giftarchiving.service.share.InstagramSharedService
import com.nexters.giftarchiving.util.ImageConverter
import com.nexters.giftarchiving.util.ImageManager
import com.nexters.giftarchiving.viewmodel.ShareInstagramViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ShareInstagramFragment :
    BaseFragment<ShareInstagramViewModel, FragmentShareInstagramBinding>() {
    override val layoutId = R.layout.fragment_share_instagram
    override val viewModel: ShareInstagramViewModel by viewModel()
    override val navArgs by navArgs<ShareInstagramFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.instagramStory) { instagramStory() }
        observe(viewModel.instagramFeed) { instagramFeed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteShareImage()
    }

    private fun checkReadPermission(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val readPermission = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            toast(NOTICE_DO_NOT_USE_INSTA_SHARE)
            if (readPermission) {
                requestPermissions(permissions, REQUEST_CODE_READ_EXTERNAL_STORAGE)
            }
            return false
        } else {
            return true
        }
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
            if (readPermission) {
                requestPermissions(permissions, REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
            } else {
                toast(NOTICE_DO_NOT_USE_INSTA_SHARE)
            }
            return false
        } else {
            return true
        }
    }

    private fun instagramStory() {
        getUriFromInstaLayout()?.let {
            InstagramSharedService.shareInstagramStory(requireActivity(), it)
        }
    }

    private fun instagramFeed() {
        getUriFromInstaLayout()?.let {
            InstagramSharedService.shareInstagramFeed(requireActivity(), it)
        }
    }

    private fun getUriFromInstaLayout(): Uri? {
        if (!checkReadPermission() || !checkWritePermission()) return null
        if (viewModel.shareImgUri == null) {
            val bitmap = ImageConverter.layoutToBitmap(binding.shareImageLayout)
            viewModel.shareImgUri = ImageManager.saveImage(requireContext().contentResolver, bitmap)
        }
        return viewModel.shareImgUri
    }

    private fun deleteShareImage() {
        viewModel.shareImgUri?.let { deleteFileUsingUri(it) }
    }

    private fun deleteFileUsingUri(uri: Uri) {
        requireContext().contentResolver.delete(uri, null, null)
    }

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 102
        private const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 103
        private const val NOTICE_DO_NOT_USE_INSTA_SHARE = "인스타 공유를 위해 접근권한이 필요합니다."
    }
}