package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.WriteResponse
import com.nexters.giftarchiving.ui.ShareFragmentArgs
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


internal class ShareViewModel : BaseViewModel() {
    val response = MutableLiveData<WriteResponse>()
    val name = MutableLiveData<String>()
    val shareKakaoMessage = LiveEvent<Unit?>()
    val shareInstagram = LiveEvent<Unit?>()

    init {
        viewModelScope.launch {
            navArgs<ShareFragmentArgs>()
                .collect {
                    response.value = it.response
                    name.value = it.name
                }
        }
    }

    fun onClickSharedInstagramStory() {}
}