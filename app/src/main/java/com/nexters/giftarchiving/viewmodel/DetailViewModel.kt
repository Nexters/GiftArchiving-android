package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.data.write.WriteCategoryMenu
import com.nexters.giftarchiving.data.write.WriteEmotionMenu
import com.nexters.giftarchiving.data.write.WriteInformationMenuList
import com.nexters.giftarchiving.data.write.WritePurposeMenu
import com.nexters.giftarchiving.model.GiftDetailResponse
import com.nexters.giftarchiving.repository.DetailRepository
import com.nexters.giftarchiving.ui.DetailFragmentArgs
import com.nexters.giftarchiving.ui.DetailFragmentDirections
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.ui.data.write.WriteFrameShape
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.DateConvert
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class DetailViewModel(private val repository: DetailRepository) : BaseViewModel() {
    val isReceiveGift = MutableLiveData<Boolean>()
    val name = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val receiveDate = MutableLiveData<String>()
    val noBgImgUrl = MutableLiveData<String>()
    val purpose = MutableLiveData(WritePurposeMenu())
    val category = MutableLiveData(WriteCategoryMenu())
    val emotion = MutableLiveData(WriteEmotionMenu())
    val backgroundColorTheme = MutableLiveData(BackgroundColorTheme.MONO)
    val clickMore = LiveEvent<Unit?>()
    val clickDelete = LiveEvent<Unit?>()

    private var giftId: String = EMPTY_STRING
    private var frameShape: WriteFrameShape = WriteFrameShape.SQUARE
    private var bgImgUrl: String = EMPTY_STRING
    private var giftDetail: GiftDetailResponse? = null

    init {
        viewModelScope.launch {
            navArgs<DetailFragmentArgs>()
                .collect {
                    giftId = it.giftId
                    val response = repository.getGift(giftId)
                    if (response.isSuccessful) {
                        giftDetail = response.body()
                        giftDetail?.let { gift -> setGiftProperties(gift) }
                    } else {
                        toast.postValue(NOTICE_FAIL_LOAD)
                    }
                }
        }
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun onClickMore() {
        clickMore.call()
    }

    fun onClickEdit() {
        navDirections.value = DetailFragmentDirections.actionDetailFragmentToWriteFragment(
            isReceiveGift = isReceiveGift.value ?: true,
            isEditMode = true,
            giftDetail = giftDetail
        )
    }

    fun onClickDelete() {
        clickDelete.call()
    }

    fun onClickShare() {
        navDirections.value = DetailFragmentDirections.actionDetailFragmentToShareFragment(
            giftId = giftId,
            isReceive = isReceiveGift.value ?: true,
            name = name.value,
            backgroundTheme = backgroundColorTheme.value ?: BackgroundColorTheme.MONO,
            frameShape = frameShape,
            noBgImgUrl = noBgImgUrl.value,
            bgImgUrl = bgImgUrl
        )
    }

    fun deleteGift() {
        viewModelScope.launch {
            repository.deleteGift(giftId)
            navDirections.postValue(BackDirections())
        }
    }

    private fun setGiftProperties(gift: GiftDetailResponse) {
        val bgColor = when (BackgroundColorTheme.values().map { it.name }.contains(gift.bgColor)) {
            true -> BackgroundColorTheme.valueOf(gift.bgColor)
            false -> BackgroundColorTheme.MONO
        }

        isReceiveGift.postValue(gift.isReceiveGift)
        name.postValue(gift.name)
        content.postValue(gift.content)
        receiveDate.postValue(DateConvert.localDateTimeStrToLocalDateStr1(gift.receiveDate))
        noBgImgUrl.postValue(gift.noBgImgUrl)
        purpose.postValue(WriteInformationMenuList.findPurpose(gift.reason))
        category.postValue(WriteInformationMenuList.findCategory(gift.category))
        emotion.postValue(WriteInformationMenuList.findEmotion(gift.emotion, gift.isReceiveGift))
        backgroundColorTheme.postValue(bgColor)
        frameShape = WriteFrameShape.valueOf(gift.frameType)
        bgImgUrl = gift.bgImgUrl
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val NOTICE_FAIL_LOAD = "선물 정보를 불러올 수 없습니다"
    }
}