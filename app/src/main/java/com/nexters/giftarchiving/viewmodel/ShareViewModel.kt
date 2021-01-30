package com.nexters.giftarchiving.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.ShareFragmentArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


internal class ShareViewModel : BaseViewModel() {
    val giftImg = MutableLiveData<Bitmap>()

    init {
        viewModelScope.launch {
            navArgs<ShareFragmentArgs>()
                .collect { giftImg.value = it.bitmap }
        }
    }

    fun onClickSharedInstagramStory() {}
}