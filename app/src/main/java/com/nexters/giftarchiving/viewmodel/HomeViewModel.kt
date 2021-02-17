package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.GiftListResponse
import com.nexters.giftarchiving.repository.GiftRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.ui.HomeFragmentDirections
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val giftRepository: GiftRepository,
    private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {
    val userId = preferenceRepository.getUserId()
    val getAllReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    val getAllNotReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    var totalReceive = 0
    var totalNotReceive = 0
    init {
        viewModelScope.launch {
            totalReceive = giftRepository.getGiftListAll(userId.toString(),0,1, true).giftListTotalCount
            totalNotReceive = giftRepository.getGiftListAll(userId.toString(),0,1, false).giftListTotalCount
            var tempCountReceive = totalReceive
            var tempCountNotReceive = totalNotReceive
            if (totalReceive==0)
                tempCountReceive=1
            if(totalNotReceive==0)
                tempCountNotReceive=1
            getAllReceivedGiftListResponse.value = giftRepository.getGiftListAll(userId.toString(),0,tempCountReceive, true)
            getAllNotReceivedGiftListResponse.value = giftRepository.getGiftListAll(userId.toString(),0,tempCountNotReceive,false)
        }
    }
    val onClickGivenListButtonListener = View.OnClickListener(){
        onClickGivenListButton()
    }

    val onClickTakenListButtonListener = View.OnClickListener(){
        onClickTakenListButton()
    }

    fun onClickWrite(isReceiveGift: Boolean){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionHomeFragmentToWriteFragment(isReceiveGift = isReceiveGift)
        }
    }

    fun onClickSearch(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionHomeFragmentToSearchFramgent()
        }
    }

    private fun onClickTakenListButton(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionTakenFragmentToListFragment("받은 선물",getAllReceivedGiftListResponse.value!!,totalReceive.toString())
        }
    }

    private fun onClickGivenListButton(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionGivenFragmentToListFragment("보낸 선물",getAllNotReceivedGiftListResponse.value!!,totalNotReceive.toString())
        }
    }

    fun onClickSetting(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
        }
    }
}