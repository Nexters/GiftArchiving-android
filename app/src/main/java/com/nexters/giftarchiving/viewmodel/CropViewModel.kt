package com.nexters.giftarchiving.viewmodel

import com.nexters.giftarchiving.base.BaseViewModel
import com.theartofdev.edmodo.cropper.CropImageView

internal class CropViewModel : BaseViewModel() {
    fun onCrop(cropImageView: CropImageView) {
        cropImageView.getCroppedImageAsync()
    }
}