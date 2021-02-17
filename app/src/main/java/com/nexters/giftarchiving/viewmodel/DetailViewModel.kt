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
        receiveDate.postValue(DateConvert.localDateTimeStrToLocalDateStr1(gift.receiveDate))
        noBgImgUrl.postValue(gift.noBgImgUrl)
        purpose.postValue(WriteInformationMenuList.findPurpose(gift.reason))
        category.postValue(WriteInformationMenuList.findCategory(gift.category))
        emotion.postValue(WriteInformationMenuList.findEmotion(gift.emotion, gift.isReceiveGift))
        backgroundColorTheme.postValue(BackgroundColorTheme.valueOf(gift.bgColor))
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}