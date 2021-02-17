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
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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

    private var giftId: String = EMPTY_STRING

    init {
        viewModelScope.launch {
            navArgs<DetailFragmentArgs>()
                .collect {
                    giftId = it.giftId
                    setGiftProperties(repository.getGift(giftId))
                }
        }
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun onClickMore() {
        clickMore.call()
    }

    fun onDelete() {
        viewModelScope.launch {
            repository.deleteGift(giftId)
            navDirections.postValue(BackDirections())
        }
    }

    private fun setGiftProperties(gift: GiftDetailResponse) {
        isReceiveGift.postValue(gift.isReceiveGift)
        name.postValue(gift.name)
        content.postValue(gift.content)
        receiveDate.postValue(convertLocalDateFormat(gift.receiveDate))
        noBgImgUrl.postValue(gift.noBgImgUrl)
        purpose.postValue(findPurpose(gift.reason))
        category.postValue(findCategory(gift.category))
        emotion.postValue(findEmotion(gift.emotion, gift.isReceiveGift))
        backgroundColorTheme.postValue(BackgroundColorTheme.valueOf(gift.bgColor))
    }

    private fun convertLocalDateFormat(dateStr: String): String {
        try {
            val localDate =
                LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT_FROM))
            return localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT_TO))
        } catch (e: DateTimeParseException) {
            toast.postValue(NOTICE_NOT_LOAD_DATE)
        }
        return EMPTY_STRING
    }

    private fun findPurpose(engName: String): WritePurposeMenu =
        WriteInformationMenuList
            .purposeMenuList
            .find { it.purposeMenu.titleEng == engName }
            ?.purposeMenu
            ?: WritePurposeMenu()

    private fun findCategory(engName: String): WriteCategoryMenu =
        WriteInformationMenuList
            .categoryMenuList
            .find { it.categoryMenu.titleEng == engName }
            ?.categoryMenu
            ?: WriteCategoryMenu()

    private fun findEmotion(engName: String, isReceive: Boolean): WriteEmotionMenu =
        when (isReceive) {
            true -> WriteInformationMenuList.receiveEmotionList
            false -> WriteInformationMenuList.sendEmotionList
        }.find { it.emotionMenu.titleEng == engName }
            ?.emotionMenu
            ?: WriteEmotionMenu()

    companion object {
        private const val EMPTY_STRING = ""

        private const val DATE_FORMAT_FROM = "yyyy-MM-dd'T'HH:mm:ss"
        private const val DATE_FORMAT_TO = "yyyy.MM.dd"

        private const val NOTICE_NOT_LOAD_DATE = "날짜를 불러올 수 없습니다"
    }
}