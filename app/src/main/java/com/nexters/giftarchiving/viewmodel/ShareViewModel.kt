package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.template.model.FeedTemplate
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.service.share.KakaoFeedMessage
import com.nexters.giftarchiving.ui.ShareFragmentArgs
import com.nexters.giftarchiving.ui.ShareFragmentDirections
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.ui.data.write.WriteFrameShape
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ShareViewModel : BaseViewModel() {
    val name = MutableLiveData<String>()
    val backgroundColorTheme = MutableLiveData<BackgroundColorTheme>()
    val frameShape = MutableLiveData<WriteFrameShape>()
    val noBgImgUrl = MutableLiveData<String>()
    val bgImgUrl = MutableLiveData<String>()
    val saveImage = LiveEvent<Unit?>()
    val shareKakaoMessage = LiveEvent<Unit?>()
    var isReceive = true

    private var giftId: String? = null

    init {
        viewModelScope.launch {
            navArgs<ShareFragmentArgs>()
                .collect {
                    giftId = it.giftId
                    isReceive = it.isReceive
                    name.value = it.name
                    backgroundColorTheme.value = it.backgroundTheme
                    frameShape.value = it.frameShape
                    noBgImgUrl.value = it.noBgImgUrl
                    bgImgUrl.value = it.bgImgUrl
                }
        }
    }

    fun getKakaoMessageFeed(): FeedTemplate? {
        return KakaoFeedMessage.getFeed(
            bgImgUrl.value ?: "",
            name.value ?: "",
            giftId ?: "",
            isReceive
        )
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun onClickSaveImage() {
        saveImage.call()
    }

    fun onClickSharedKakaoMessage() {
        shareKakaoMessage.call()
    }

    fun onClickSharedInstagram() {
        navDirections.value = ShareFragmentDirections.actionShareFragmentToShareInstagramFragment(
            noBgImgUrl.value,
            name.value,
            backgroundColorTheme.value ?: BackgroundColorTheme.MONO,
            isReceive
        )
    }
}