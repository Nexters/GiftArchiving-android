package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.GiftListResponse
import com.nexters.giftarchiving.repository.GiftRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.repository.WriteRepository
import com.nexters.giftarchiving.util.BackDirections
import kotlinx.coroutines.launch

internal class SearchViewModel(
    private val giftRepository: GiftRepository,
    private val preferenceRepository: PreferenceRepository
): BaseViewModel(){
    val getAllReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    val getAllNotReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    val userId = preferenceRepository.getUserId()
    fun onClickBack() {
        navDirections.value = BackDirections()
    }
    init {
        viewModelScope.launch {
            getAllReceivedGiftListResponse.value = giftRepository.getGiftListAll(userId.toString(),0,50, true)
            getAllNotReceivedGiftListResponse.value = giftRepository.getGiftListAll(userId.toString(),0,50,true)
        }
    }
}