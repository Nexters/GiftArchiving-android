package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.template.model.FeedTemplate
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.WriteResponse
import com.nexters.giftarchiving.service.share.KakaoFeedMessage
import com.nexters.giftarchiving.ui.ShareFragmentArgs
import com.nexters.giftarchiving.ui.ShareFragmentDirections
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.ui.data.write.WriteFrameShape
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ShareViewModel : BaseViewModel() {
    val response = MutableLiveData<WriteResponse>()
    val name = MutableLiveData<String>()
    val backgroundColorTheme = MutableLiveData<BackgroundColorTheme>()
    val frameShape = MutableLiveData<WriteFrameShape>()
    val saveImage = LiveEvent<Unit?>()
    val shareKakaoMessage = LiveEvent<Unit?>()
    var isReceive = true

    init {
        viewModelScope.launch {
            navArgs<ShareFragmentArgs>()
                .collect {
                    response.value = it.response
                    name.value = it.name
                    backgroundColorTheme.value = it.backgroundTheme
                    frameShape.value = it.frameShape
                    isReceive = it.isReceive
                }
        }
    }

    fun getKakaoMessageFeed(): FeedTemplate? {
        return response.value?.let {
            KakaoFeedMessage.getFeed(it.bgImgUrl, name.value ?: "")
        }
    }

    fun onClickBackHome() {
        navDirections.value = ShareFragmentDirections.actionShareFragmentToHomeFragment()
    }

    fun onClickSaveImage() {
        saveImage.call()
    }

    fun onClickSharedKakaoMessage() {
        shareKakaoMessage.call()
    }

    fun onClickSharedInstagram() {
        navDirections.value = ShareFragmentDirections.actionShareFragmentToShareInstagramFragment(
            response.value?.noBgImgUrl,
            name.value,
            backgroundColorTheme.value ?: BackgroundColorTheme.MONO,
            isReceive
        )
    }
}