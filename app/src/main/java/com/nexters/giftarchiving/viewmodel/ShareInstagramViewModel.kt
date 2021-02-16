package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.ShareInstagramFragmentArgs
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ShareInstagramViewModel: BaseViewModel() {
    val noBgImgUrl = MutableLiveData<String>()
    val backgroundColorTheme = MutableLiveData<BackgroundColorTheme>()
    val name = MutableLiveData<String>()
    val instagramStory = LiveEvent<Unit?>()
    val instagramFeed = LiveEvent<Unit?>()

    init {
        viewModelScope.launch {
            navArgs<ShareInstagramFragmentArgs>()
                .collect {
                    noBgImgUrl.value = it.noBgUrl
                    backgroundColorTheme.value = it.backgroundTheme
                    name.value = it.name
                }
        }
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun onClickShareInstagramStory() {
        instagramStory.call()
    }

    fun onClickShareInstagramFeed() {
        instagramFeed.call()
    }
}