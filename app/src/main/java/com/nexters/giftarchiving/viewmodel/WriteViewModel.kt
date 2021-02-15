package com.nexters.giftarchiving.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.data.write.WriteCategoryMenu
import com.nexters.giftarchiving.data.write.WriteEmotionMenu
import com.nexters.giftarchiving.data.write.WriteInformationMenu
import com.nexters.giftarchiving.data.write.WritePurposeMenu
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.repository.WriteRepository
import com.nexters.giftarchiving.ui.WriteFragmentArgs
import com.nexters.giftarchiving.ui.WriteFragmentDirections
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.ui.data.write.WriteFrameShape
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.ui.data.write.WriteStickerTabLayoutTheme
import com.nexters.giftarchiving.util.ImageConverter
import com.theartofdev.edmodo.cropper.CropImage
import com.xiaopo.flying.sticker.Sticker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File
import java.time.LocalDate

internal class WriteViewModel(
    private val writeRepository: WriteRepository,
    private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {
    val editedImage = MutableLiveData<Bitmap?>()
    val frameShape = MutableLiveData(WriteFrameShape.RECTANGLE)
    val backgroundColorTheme = MutableLiveData(BackgroundColorTheme.MONO)
    val stickerViewPagerTheme = MutableLiveData(WriteStickerTabLayoutTheme.DARK_MODE)
    val date = MutableLiveData(LocalDate.now())
    val category = MutableLiveData(WriteCategoryMenu())
    val purpose = MutableLiveData(WritePurposeMenu())
    val emotion = MutableLiveData(WriteEmotionMenu())
    val name = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val isLoading = MutableLiveData(false)
    val showMenuType = LiveEvent<WriteMenu>()
    val hideMenuType = LiveEvent<WriteMenu>()
    val changeDate = LiveEvent<Unit?>()
    val addSticker = LiveEvent<Unit?>()
    val loadGallery = LiveEvent<Unit?>()
    val isSaved = LiveEvent<Unit?>()

    var stickerList = mutableListOf<Sticker>()
    var baseImageUri: Uri? = null
    private var isReceiveGift = true

    init {
        viewModelScope.launch {
            navArgs<WriteFragmentArgs>()
                .collect {
                    editedImage.value = it.bitmap
                    isReceiveGift = it.isReceiveGift
                }
        }
    }

    fun setInformationMenu(item: WriteInformationMenu) {
        when (showMenuType.value) {
            WriteMenu.INFORMATION_CATEGORY -> category.value = item as WriteCategoryMenu
            WriteMenu.INFORMATION_PURPOSE -> purpose.value = item as WritePurposeMenu
            WriteMenu.INFORMATION_EMOTION -> emotion.value = item as WriteEmotionMenu
            else -> return
        }

        hideMenuType.value = showMenuType.value
    }

    fun setNewImage(uri: Uri, img: Bitmap) {
        baseImageUri = uri
        editedImage.value = img
    }

    fun loadGallery() {
        loadGallery.call()
    }

    fun setFrameShape(shape: WriteFrameShape) {
        frameShape.value = shape
        convertImageShape()
    }

    fun setBackgroundColor(colorTheme: BackgroundColorTheme) {
        backgroundColorTheme.value = colorTheme
        stickerViewPagerTheme.value = when (colorTheme.isDarkMode) {
            true -> WriteStickerTabLayoutTheme.DARK_MODE
            false -> WriteStickerTabLayoutTheme.LIGHT_MODE
        }
    }

    fun attachSticker() {
        addSticker.call()
        hideMenuType.value = WriteMenu.STICKER
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun setShowMenuType(menuType: WriteMenu) {
        if (menuType != showMenuType.value) {
            showMenuType.value?.let { setHideMenuType(it) }
        }
        showMenuType.value = menuType
    }

    fun setHideMenuType(menuType: WriteMenu) {
        hideMenuType.value = menuType
    }

    fun changeDate() {
        changeDate.call()
        setHideMenuType(WriteMenu.DATE)
    }

    fun onClickNext() {
        isLoading.value = true
        isSaved.call()
    }

    fun goNext(parentDir: File, noBgBitmap: Bitmap, bgBitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val noBgImg = bitmapToMultipartBody(parentDir, noBgBitmap, "noBgImg")
            val bgImg = bitmapToMultipartBody(parentDir, bgBitmap, "bgImg")
            if (bgImg != null && noBgImg != null) {
                val response = writeRepository.createGift(
                    createdBy = preferenceRepository.getUserId().toString(),
                    isReceiveGift = isReceiveGift,
                    name = name.value ?: "",
                    content = content.value ?: "",
                    receiveDate = date.value ?: LocalDate.now(),
//                    category = category.value?.title ?: "",
//                    reason = purpose.value?.title ?: "",
//                    emotion = emotion.value?.title ?: "",
                    category = "LIVING",
                    reason = "BIRTHDAY",
                    emotion = "GOOD",
                    bgColor = backgroundColorTheme.value?.toString() ?: "",
                    noBgImg = noBgImg,
                    bgImg = bgImg
                )

                isLoading.postValue(false)
                val directions =
                    WriteFragmentDirections.actionWriteFragmentToShareFragment(
                        name.value,
                        response,
                        backgroundColorTheme.value ?: BackgroundColorTheme.MONO
                    )
                navDirections.postValue(directions)
            } else {
                toast.postValue(NOTICE_FAIL_CONVERT_IMG)
            }
        }
    }

    fun convertLayoutToBitmap(v: View): Bitmap {
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(bmp)
        v.draw(canvas)
        return bmp
    }

    fun delayAndCallback(callback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(100L)
            callback()
        }
    }

    private fun convertImageShape() {
        val shape = frameShape.value ?: WriteFrameShape.RECTANGLE
        originBitmap?.let { bm ->
            editedImage.value = when (shape) {
                WriteFrameShape.RECTANGLE -> bm
                WriteFrameShape.OVAL -> CropImage.toOvalBitmap(bm)
                WriteFrameShape.WINDOW -> CropImage.toWindowBitmap(bm)
            }
        }
        hideMenuType.value = WriteMenu.FRAME
    }

    private fun bitmapToMultipartBody(
        parentDir: File,
        bitmap: Bitmap,
        multipartName: String
    ): MultipartBody.Part? {
        return ImageConverter.bitmapToMultipartBody(bitmap, parentDir, null, multipartName)
    }

    companion object {
        @JvmStatic
        val NOTICE_SELECT_IMAGE = "이미지를 선택하세요"

        @JvmStatic
        val NOTICE_FAIL_CONVERT_IMG = "이미지 변환에 실패하였습니다"
    }
}