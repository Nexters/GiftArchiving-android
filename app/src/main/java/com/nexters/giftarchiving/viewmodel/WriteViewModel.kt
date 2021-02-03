package com.nexters.giftarchiving.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.WriteFragmentArgs
import com.nexters.giftarchiving.ui.WriteFragmentDirections
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.xiaopo.flying.sticker.Sticker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

internal class WriteViewModel : BaseViewModel() {
    val editedImage = MutableLiveData<Bitmap?>()
    val backgroundColorTheme = MutableLiveData(BackgroundColorTheme.MONO)
    val date = MutableLiveData(LocalDate.now())
    val name = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val isShowDatePicker = MutableLiveData<Boolean>()
    val changeDate = LiveEvent<Unit?>()
    val addSticker = LiveEvent<Unit?>()
    val loadGallery = LiveEvent<Unit?>()
    val isSaved = LiveEvent<Unit?>()

    var stickerList = mutableListOf<Sticker>()
    var baseImageUri: Uri? = null

    init {
        viewModelScope.launch {
            navArgs<WriteFragmentArgs>()
                .collect { editedImage.value = it.bitmap }
        }
    }

    fun setNewImage(uri: Uri, img: Bitmap) {
        baseImageUri = uri
        editedImage.value = img
    }

    fun loadGallery() {
        loadGallery.call()
    }

    fun loadCropEditor() {
        baseImageUri?.let {
            navDirections.value =
                WriteFragmentDirections.actionWriteFragmentToCropFragment(it.toString())
        }
    }

    fun setBackgroundColor(colorTheme: BackgroundColorTheme) {
        backgroundColorTheme.value = colorTheme
    }

    fun attachSticker() {
        if (editedImage.value != null) {
            addSticker.call()
        }
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun showDatePicker() {
        isShowDatePicker.value = true
    }

    fun hideDatePicker() {
        isShowDatePicker.value = false
    }

    fun changeDate() {
        changeDate.call()
        hideDatePicker()
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