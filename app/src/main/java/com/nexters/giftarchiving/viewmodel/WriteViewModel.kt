package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.util.theme.BackgroundColorTheme

internal class WriteViewModel : BaseViewModel() {
    val stickerResources = MutableLiveData<MutableList<Int>>()
    val backgroundColorTheme = MutableLiveData<BackgroundColorTheme>()

    private val stickerResourceList = ArrayList<Int>()

    }

    fun setBackgroundColor(colorTheme: BackgroundColorTheme) {
        backgroundColorTheme.value = colorTheme
    }

    fun attachSticker() {
        stickerResourceList.add(R.drawable.ic_launcher_foreground)
        stickerResources.value = stickerResourceList
    }

    private fun convertLayoutToBitmap(v: View): Bitmap {
        val bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        v.draw(canvas)
        return bitmap
    }
}