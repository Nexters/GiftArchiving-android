package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.R
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
    val currentBgColor = MutableLiveData(R.color.gray)
    val currentFrame = MutableLiveData("SQUARE")
    var totalReceive = 0
    var totalNotReceive = 0
    var needReload = false
    init {
        getAllList()
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
            navDirections.value=HomeFragmentDirections.actionTakenFragmentToListFragment("받은 선물")
        }
    }

    private fun onClickGivenListButton(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionGivenFragmentToListFragment("보낸 선물")
        }
    }

    fun onClickSetting(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
        }
    }

    fun setCurrentBgColorAndFrame(bgColor : Int, frame : String){
        currentBgColor.value = bgColor
        currentFrame.value = frame
    }

    fun getAllList() {
        getAllReceivedGiftListResponse.value = GiftListResponse(listOf(), 0, 0, 0)
        getAllNotReceivedGiftListResponse.value = GiftListResponse(listOf(), 0, 0, 0)
        viewModelScope.launch {
            val receiveGiftCntResponse =
                giftRepository.getGiftListAll(userId.toString(), 0, 1, true)
            val sendGiftListCntResponse =
                giftRepository.getGiftListAll(userId.toString(), 0, 1, false)
            if (receiveGiftCntResponse.isSuccessful && sendGiftListCntResponse.isSuccessful) {
                totalReceive = receiveGiftCntResponse.body()?.giftListTotalCount ?: 0
                totalNotReceive = sendGiftListCntResponse.body()?.giftListTotalCount ?: 0
                var tempCountReceive = totalReceive
                var tempCountNotReceive = totalNotReceive
                if (totalReceive == 0)
                    tempCountReceive = 1
                if (totalNotReceive == 0)
                    tempCountNotReceive = 1
                val receiveGiftListResponse =
                    giftRepository.getGiftListAll(userId.toString(), 0, tempCountReceive, true)
                val sendGiftListResponse =
                    giftRepository.getGiftListAll(userId.toString(), 0, tempCountNotReceive, false)
                if (receiveGiftListResponse.isSuccessful && sendGiftListResponse.isSuccessful) {
                    getAllReceivedGiftListResponse.value = receiveGiftListResponse.body()
                    getAllNotReceivedGiftListResponse.value = sendGiftListResponse.body()
                } else {
                    toast.postValue(NOTICE_FAIL_LOAD_LIST)
                }
            } else {
                toast.postValue(NOTICE_FAIL_LOAD_LIST)
            }
        }
    }

    fun onClickDetail(giftId: String) {
        navDirections.value = HomeFragmentDirections.actionHomeFragmentToDetailFragment(giftId)
    }

    companion object {
        private const val NOTICE_FAIL_LOAD_LIST = "선물 리스트를 불러올 수 없습니다"
    }
}