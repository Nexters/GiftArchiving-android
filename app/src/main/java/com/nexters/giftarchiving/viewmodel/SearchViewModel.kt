package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.GiftListResponse
import com.nexters.giftarchiving.model.GiftResponse
import com.nexters.giftarchiving.repository.GiftRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.util.BackDirections
import kotlinx.coroutines.launch

internal class SearchViewModel(
    private val giftRepository: GiftRepository,
    private val preferenceRepository: PreferenceRepository
): BaseViewModel(){
    val getAllReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    val getAllNotReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    val getAllName = MutableLiveData(arrayListOf<String>())
    val userId = preferenceRepository.getUserId()
    val totalCount = MutableLiveData(0)
    val searchResult = MutableLiveData(listOf<GiftResponse>())
    val searchResultGiven = MutableLiveData(arrayListOf<GiftResponse>())
    val searchResultTaken = MutableLiveData(arrayListOf<GiftResponse>())

    var fragmentType = MutableLiveData(0)

    val currentCategory = MutableLiveData<String?>(null)
    val currentReason = MutableLiveData<String?>(null)
    val currentSearchText = MutableLiveData<String?>(null)

    fun onClickBack() {
        if(fragmentType.value==0){
            navDirections.value = BackDirections()
        }
        currentCategory.value = null
        currentReason.value = null
        currentSearchText.value = null
        fragmentType.value=0
    }

    val onClickCategoryListener = View.OnClickListener(){
        when(it.id){
            R.id.category_mobile_coupon->onClickCategory("GIFT_CARD")
            R.id.category_fashion->onClickCategory("FASHION")
            R.id.category_cosmetic->onClickCategory("BEAUTY")
            R.id.category_food->onClickCategory("FOOD")
            R.id.category_voucher->onClickCategory("VOUCHER")
            R.id.category_digital->onClickCategory("DIGITAL")
            R.id.category_sports->onClickCategory("SPORTS")
            R.id.category_baby->onClickCategory("BABY")
            R.id.category_pet->onClickCategory("PET")
            R.id.category_living->onClickCategory("LIVING")
            R.id.category_culture->onClickCategory("CULTURE")
            else->onClickCategory("ETC")
        }
        viewModelScope.launch {
            searchResult.value = searchByCategory()
            if(searchResult.value!!.isEmpty()){
                fragmentType.value=2
            } else{
                fragmentType.value=1
            }
        }
    }

    val onClickReasonListener = View.OnClickListener {
        when(it.id){
            R.id.situation_birthday->onClickReason("BIRTHDAY")
            R.id.situation_anniversary->onClickReason("ANNIVERSARY")
            R.id.situation_getJob->onClickReason("EMPLOYMENT")
            R.id.situation_housewarming->onClickReason("HOUSES")
            R.id.situation_wedding->onClickReason("MARRIAGE")
            R.id.situation_study->onClickReason("STUDY")
            R.id.situation_holiday->onClickReason("HOLIDAY")
            R.id.situation_cheer->onClickReason("CHEER_UP")
            R.id.situation_apology->onClickReason("APOLOGIZE")
            R.id.situation_thanks->onClickReason("THANKS")
            R.id.situation_noReason->onClickReason("JUST")
            else->onClickReason("ETC")
        }
        viewModelScope.launch {
            searchResult.value = searchByReason()
            if(searchResult.value!!.isEmpty()){
                fragmentType.value=2
            } else{
                fragmentType.value=1
            }
        }
    }

    private fun onClickCategory(tag : String){
        currentCategory.value = tag
    }

    fun onClickReason(tag : String){
        currentReason.value = tag
    }

    fun setCurrentSearchText(tag : String){
        currentSearchText.value = tag
        viewModelScope.launch {
            searchResult.value = searchByText()
            if(searchResult.value!!.isEmpty()){
                fragmentType.value=2
            } else{
                fragmentType.value=1
            }
        }
    }

    suspend fun searchByCategory() : List<GiftResponse>{
        val all = giftRepository.getGiftListByCategory(userId.toString(),currentCategory.value,totalCount.value).giftListGifts
        for(item in all){
            if (item.isReceiveGift){
                searchResultTaken.value!!.add(item)
            } else{
                searchResultGiven.value!!.add(item)
            }
        }
        return all
    }

    suspend fun searchByReason() : List<GiftResponse>{
        val all = giftRepository.getGiftListByReason(userId.toString(),currentReason.value,totalCount.value).giftListGifts
        for(item in all){
            if (item.isReceiveGift){
                searchResultTaken.value!!.add(item)
            } else{
                searchResultGiven.value!!.add(item)
            }
        }
        return all
    }

    suspend fun searchByText() : List<GiftResponse>{
        val temp = mutableSetOf<GiftResponse>()
        temp.addAll(giftRepository.getGiftListByName(userId.toString(),currentSearchText.value,currentCategory.value,currentReason.value,totalCount.value).giftListGifts)
        temp.addAll(giftRepository.getGiftListByContent(userId.toString(),currentSearchText.value,currentCategory.value,currentReason.value,totalCount.value).giftListGifts)
        val result = arrayListOf<GiftResponse>()
        result.addAll(temp)
        for(item in result){
            if (item.isReceiveGift){
                searchResultTaken.value!!.add(item)
            } else{
                searchResultGiven.value!!.add(item)
            }
        }
        return result
    }

    init {
        viewModelScope.launch {
            totalCount.value = giftRepository.getGiftListAll(userId.toString(),0,50, true).giftListTotalCount
            val tempSet = mutableSetOf<String>()
            if(totalCount.value==0){
                totalCount.value=1
            }
            getAllReceivedGiftListResponse.value = giftRepository.getGiftListAll(userId.toString(),0, totalCount.value!!, true)
            getAllNotReceivedGiftListResponse.value = giftRepository.getGiftListAll(userId.toString(),0,totalCount.value!!,false)
            for (item in getAllReceivedGiftListResponse.value!!.giftListGifts){
                tempSet.add(item.giftName)
            }
            for (item in getAllNotReceivedGiftListResponse.value!!.giftListGifts){
                tempSet.add(item.giftName)
            }
            getAllName.value!!.addAll(tempSet)
        }
    }
}