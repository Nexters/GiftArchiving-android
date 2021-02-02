package com.nexters.giftarchiving.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.WriteFragmentArgs
import com.nexters.giftarchiving.ui.WriteFragmentDirections
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent
import com.nexters.giftarchiving.util.theme.BackgroundColorTheme
import com.xiaopo.flying.sticker.Sticker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

internal class WriteViewModel : BaseViewModel() {
    var stickerList = mutableListOf<Sticker>()
    val image = MutableLiveData<Bitmap?>()
    val backgroundColorTheme = MutableLiveData(BackgroundColorTheme.MONO)
    val date = MutableLiveData(LocalDate.now())
    val name = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val addSticker = LiveEvent<Unit?>()
    val loadGallery = LiveEvent<Unit?>()
    val isSaved = LiveEvent<Unit?>()

    init {
        viewModelScope.launch {
            navArgs<WriteFragmentArgs>()
                .collect { image.value = it.bitmap }
        }
    }

    fun loadGallery() {
        loadGallery.call()
    }

    fun setBackgroundColor(colorTheme: BackgroundColorTheme) {
        backgroundColorTheme.value = colorTheme
    }

    fun attachSticker() {
        if (image.value != null) {
            addSticker.call()
        }
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun onClickNext(v: View) {
        isSaved.call()
        val bitmap = convertLayoutToBitmap(v)
        navDirections.value = WriteFragmentDirections.actionWriteFragmentToShareFragment(bitmap)
    }

    private fun convertLayoutToBitmap(v: View): Bitmap {
        val bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        v.draw(canvas)
        return bitmap
    }
}