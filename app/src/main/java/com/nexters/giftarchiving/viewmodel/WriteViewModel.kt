package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.util.color.BackgroundColor
import com.nexters.giftarchiving.util.color.FontColor

internal class WriteViewModel : BaseViewModel() {
    val backgroundColor = MutableLiveData<BackgroundColor>()
    val fontColor = MutableLiveData<FontColor>()
    val stickerResources = MutableLiveData<MutableList<Int>>()

    private val stickerResourceList = ArrayList<Int>()

    fun setBackgroundColor(color: BackgroundColor) {
        backgroundColor.value = color
    }

    fun setFontColor(color: FontColor) {
        fontColor.value = color
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