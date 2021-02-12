package com.nexters.giftarchiving.viewmodel

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
    lateinit var getAllReceivedGiftListResponse : GiftListResponse
    lateinit var getAllNotReceivedGiftListResponse : GiftListResponse
    val userId = preferenceRepository.getUserId()
    fun onClickBack() {
        navDirections.value = BackDirections()
    }
    init {
        viewModelScope.launch {
            getAllReceivedGiftListResponse = giftRepository.getGiftListAll(userId.toString(),0,50, true)
            getAllNotReceivedGiftListResponse = giftRepository.getGiftListAll(userId.toString(),0,50,true)
        }
    }
}