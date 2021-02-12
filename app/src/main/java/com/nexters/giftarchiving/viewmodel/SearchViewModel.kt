package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.data.room.LatestSearchDB
import com.nexters.giftarchiving.data.room.LatestSearchDao
import com.nexters.giftarchiving.model.GiftListResponse
import com.nexters.giftarchiving.repository.GiftRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.repository.WriteRepository
import com.nexters.giftarchiving.util.BackDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SearchViewModel(
    private val giftRepository: GiftRepository,
    private val preferenceRepository: PreferenceRepository
): BaseViewModel(){
    val getAllReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    val getAllNotReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    val userId = preferenceRepository.getUserId()
    val getSearchResult = MutableLiveData(GiftListResponse(listOf(),0,0,0))

    val currentCategory = MutableLiveData("")
    val currentReason = MutableLiveData("")
    val currentSearchText = MutableLiveData("")

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    val onClickTagListener = View.OnClickListener(){

    }

    fun onClickCategory(tag : String){
        currentCategory.value = tag
    }

    fun onClickReason(tag : String){
        currentReason.value = tag
    }

    fun onClickName(tag : String){
    }

    suspend fun search(){
        getSearchResult.value = giftRepository.getGiftListByTag(userId.toString(),currentCategory.value,null,currentReason.value,currentSearchText.value,currentSearchText.value,0,50)
    }

    init {
        viewModelScope.launch {
            getAllReceivedGiftListResponse.value = giftRepository.getGiftListAll(userId.toString(),0,50, true)
            getAllNotReceivedGiftListResponse.value = giftRepository.getGiftListAll(userId.toString(),0,50,true)
        }
    }
}