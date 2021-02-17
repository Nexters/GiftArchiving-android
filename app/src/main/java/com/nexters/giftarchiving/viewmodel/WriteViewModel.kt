package com.nexters.giftarchiving.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.data.write.WriteCategoryMenu
import com.nexters.giftarchiving.data.write.WriteEmotionMenu
import com.nexters.giftarchiving.data.write.WriteInformationMenu
import com.nexters.giftarchiving.data.write.WriteInformationMenuList
import com.nexters.giftarchiving.data.write.WritePurposeMenu
import com.nexters.giftarchiving.model.GiftDetailResponse
import com.nexters.giftarchiving.model.GiftUpdate
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
import com.nexters.giftarchiving.util.DateConvert
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
    val frameShape = MutableLiveData(WriteFrameShape.SQUARE)
    val backgroundColorTheme = MutableLiveData(BackgroundColorTheme.MONO)
    val stickerViewPagerTheme = MutableLiveData(WriteStickerTabLayoutTheme.DARK_MODE)
    val date = MutableLiveData(LocalDate.now())
    val category = MutableLiveData(WriteCategoryMenu())
    val purpose = MutableLiveData(WritePurposeMenu())
    val emotion = MutableLiveData(WriteEmotionMenu())
    val name = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val isLoading = MutableLiveData(false)
    val currentMenuType = MutableLiveData<WriteMenu>()
    val showMenuType = LiveEvent<WriteMenu>()
    val hideMenuType = LiveEvent<WriteMenu>()
    val changeDate = LiveEvent<Unit?>()
    val addSticker = LiveEvent<Unit?>()
    val loadGallery = LiveEvent<Unit?>()
    val isSaved = LiveEvent<Unit?>()

    var isReceiveGift = true
    var isEditMode = false
    var noBgImgUrl: String? = null
    var stickerList = mutableListOf<Sticker>()
    var originBitmap: Bitmap? = null

    private var giftId = ""

    init {
        viewModelScope.launch {
            navArgs<WriteFragmentArgs>()
                .collect {
                    isEditMode = it.isEditMode
                    isReceiveGift = it.isReceiveGift
                    if (it.isEditMode && it.giftDetail != null) {
                        setGiftProperties(it.giftDetail)
                    }
                }
        }
    }

    private fun setGiftProperties(gift: GiftDetailResponse) {
        giftId = gift.id
        isReceiveGift = gift.isReceiveGift
        noBgImgUrl = gift.noBgImgUrl
        name.postValue(gift.name)
        content.postValue(gift.content)
        date.postValue(DateConvert.localDateTimeStrToLocalDate(gift.receiveDate))
        backgroundColorTheme.postValue(BackgroundColorTheme.valueOf(gift.bgColor))
        frameShape.postValue(WriteFrameShape.valueOf(gift.frameType))
        purpose.postValue(WriteInformationMenuList.findPurpose(gift.reason))
        category.postValue(WriteInformationMenuList.findCategory(gift.category))
        emotion.postValue(WriteInformationMenuList.findEmotion(gift.emotion, isReceiveGift))
    }

    fun setInformationMenu(item: WriteInformationMenu) {
        when (currentMenuType.value) {
            WriteMenu.INFORMATION_CATEGORY -> category.value = item as WriteCategoryMenu
            WriteMenu.INFORMATION_PURPOSE -> purpose.value = item as WritePurposeMenu
            WriteMenu.INFORMATION_EMOTION -> emotion.value = item as WriteEmotionMenu
            else -> return
        }
        hideCurrentMenu()
    }

    fun setNewImage(img: Bitmap) {
        originBitmap = img.copy(Bitmap.Config.ARGB_8888, true)
        convertImageShape()
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
        hideMenu(WriteMenu.STICKER)
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    private fun showMenu(menuType: WriteMenu) {
        currentMenuType.value = menuType
        showMenuType.value = menuType
    }

    private fun hideMenu(menuType: WriteMenu) {
        currentMenuType.value = null
        hideMenuType.value = menuType
    }

    fun hideCurrentMenu() {
        currentMenuType.value?.let { hideMenu(it) }
    }

    fun setShowMenuType(menuType: WriteMenu) {
        if (currentMenuType.value != null && menuType != currentMenuType.value) {
            hideCurrentMenu()
        }
        showMenu(menuType)
    }

    fun setHideMenuType(menuType: WriteMenu) {
        hideMenu(menuType)
    }

    fun changeDate() {
        changeDate.call()
        setHideMenuType(WriteMenu.DATE)
    }

    fun onClickNext() {
        isLoading.value = true
        if(isEditMode) {
            editGiftProperties()
        } else {
            isSaved.call()
        }
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
                    category = category.value?.titleEng ?: "",
                    reason = purpose.value?.titleEng ?: "",
                    emotion = emotion.value?.titleEng ?: "",
                    frameType = (frameShape.value ?: WriteFrameShape.SQUARE).toString(),
                    bgColor = backgroundColorTheme.value?.toString() ?: "",
                    noBgImg = noBgImg,
                    bgImg = bgImg
                )

                val directions =
                    WriteFragmentDirections.actionWriteFragmentToShareFragment(
                        giftId = response.id,
                        isReceive = isReceiveGift,
                        name = name.value,
                        backgroundTheme = backgroundColorTheme.value
                            ?: BackgroundColorTheme.MONO,
                        frameShape = frameShape.value ?: WriteFrameShape.SQUARE,
                        noBgImgUrl = response.noBgImgUrl,
                        bgImgUrl = response.bgImgUrl
                    )
                navDirections.postValue(directions)
            } else {
                toast.postValue(NOTICE_FAIL_CONVERT_IMG)
            }
        }
        isLoading.postValue(false)
    }

    fun convertLayoutToBitmap(v: View): Bitmap {
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
        val shape = frameShape.value ?: WriteFrameShape.SQUARE
        originBitmap?.let { bm ->
            editedImage.value = when (shape) {
                WriteFrameShape.SQUARE -> bm
                WriteFrameShape.CIRCLE -> CropImage.toOvalBitmap(bm)
                WriteFrameShape.ARCH -> CropImage.toWindowBitmap(bm)
            }
        }
        hideMenu(WriteMenu.FRAME)
    }

    private fun bitmapToMultipartBody(
        parentDir: File,
        bitmap: Bitmap,
        multipartName: String
    ): MultipartBody.Part? {
        return ImageConverter.bitmapToMultipartBody(bitmap, parentDir, null, multipartName)
    }

    private fun needMoreEdit() =
        name.value.isNullOrEmpty()
                || content.value.isNullOrEmpty()
                || category.value?.title == WriteCategoryMenu().title
                || purpose.value?.title == WritePurposeMenu().title
                || emotion.value?.title == WriteEmotionMenu().title

    private fun needMoreWrite() =
        originBitmap == null || needMoreEdit()

    private fun editGiftProperties() {
        viewModelScope.launch(Dispatchers.IO) {
            val receiveDate =
                DateConvert.localDateToLocalDateTimeStr(date.value ?: LocalDate.now())
            val response = writeRepository.updateGift(
                giftId, GiftUpdate(
                    content = content.value ?: "",
                    name = name.value ?: "",
                    receiveDate = receiveDate,
                    category = category.value?.titleEng ?: "",
                    emotion = emotion.value?.titleEng ?: "",
                    reason = purpose.value?.titleEng ?: "",
                    bgColor = (backgroundColorTheme.value ?: BackgroundColorTheme.MONO).toString()
                )
            )

            if(response.isSuccessful) toast.postValue("수정완료")
            else toast.postValue("수정실패")

            navDirections.postValue(BackDirections())
        }
    }

    companion object {
        @JvmStatic
        val INFORMATION_NUMBER_OF_PAGE = 8

        @JvmStatic
        val NOTICE_SELECT_IMAGE = "이미지를 선택하세요"

        @JvmStatic
        val NOTICE_FAIL_CONVERT_IMG = "이미지 변환에 실패하였습니다"
    }
}