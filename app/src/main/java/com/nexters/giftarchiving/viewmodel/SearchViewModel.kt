package com.nexters.giftarchiving.viewmodel

import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.GiftListResponse
import com.nexters.giftarchiving.repository.GiftRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.repository.WriteRepository
import com.nexters.giftarchiving.util.BackDirections

internal class SearchViewModel(
    private val writeRepository: WriteRepository,
    private val preferenceRepository: PreferenceRepository
): BaseViewModel(){
    lateinit var getAllGiftListResponse : GiftListResponse
    fun onClickBack() {
        navDirections.value = BackDirections()
    }
    init {
        getAllGiftListResponse = 
    }
}