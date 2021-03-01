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
import com.nexters.giftarchiving.ui.ListFragmentDirections
import com.nexters.giftarchiving.ui.SearchFragmentDirections
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
    val currentSearchText = MutableLiveData<String>("")

    fun onClickBack() {
        if(fragmentType.value==0){
            navDirections.value = BackDirections()
        }
        currentCategory.value = null
        currentReason.value = null
        currentSearchText.value = ""
        fragmentType.value=0
        searchResult.value = listOf()
        searchResultGiven.value!!.clear()
        searchResultTaken.value!!.clear()
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

    suspend fun searchByCategory(): List<GiftResponse> {
        val giftResponse = giftRepository.getGiftListByCategory(
            userId.toString(),
            currentCategory.value,
            totalCount.value
        )
        var all = listOf<GiftResponse>()
        if (giftResponse.isSuccessful) {
            all = giftResponse.body()?.giftListGifts ?: listOf()
            for (item in all) {
                if (item.isReceiveGift) {
                    searchResultTaken.value!!.add(item)
                } else {
                    searchResultGiven.value!!.add(item)
                }
            }
        } else {
            toast.postValue(NOTICE_FAIL_SEARCH)
        }
        return all
    }

    suspend fun searchByReason(): List<GiftResponse> {
        val giftResponse = giftRepository.getGiftListByReason(
            userId.toString(),
            currentReason.value,
            totalCount.value
        )
        var all = listOf<GiftResponse>()
        if (giftResponse.isSuccessful) {
            all = giftResponse.body()?.giftListGifts ?: listOf()
            for (item in all) {
                if (item.isReceiveGift) {
                    searchResultTaken.value!!.add(item)
                } else {
                    searchResultGiven.value!!.add(item)
                }
            }
        } else {
            toast.postValue(NOTICE_FAIL_SEARCH)
        }
        return all
    }

    suspend fun searchByText() : List<GiftResponse>{
        val temp = mutableSetOf<GiftResponse>()
        val result = arrayListOf<GiftResponse>()
        val giftByNameResponse = giftRepository.getGiftListByName(
            userId.toString(),
            currentSearchText.value,
            currentCategory.value,
            currentReason.value,
            totalCount.value
        )
        val giftByContentResponse = giftRepository.getGiftListByContent(
            userId.toString(),
            currentSearchText.value,
            currentCategory.value,
            currentReason.value,
            totalCount.value
        )
        if(giftByNameResponse.isSuccessful && giftByContentResponse.isSuccessful) {
            temp.addAll(giftByNameResponse.body()?.giftListGifts ?: setOf())
            temp.addAll(giftByContentResponse.body()?.giftListGifts ?: setOf())
            result.addAll(temp)
            for (item in result) {
                if (item.isReceiveGift){
                    searchResultTaken.value!!.add(item)
                } else{
                    searchResultGiven.value!!.add(item)
                }
            }
        } else {
            toast.postValue(NOTICE_FAIL_SEARCH)
        }
        return result
    }

    fun onClickDetail(giftId: String) {
        navDirections.value = SearchFragmentDirections.actionSearchFragmentToDetailFragment(giftId)
    }

    init {
        viewModelScope.launch {
            val receiveGiftCntResponse =
                giftRepository.getGiftListAll(userId.toString(), 0, 1, true)
            val sendGiftListCntResponse =
                giftRepository.getGiftListAll(userId.toString(), 0, 1, false)
            if (receiveGiftCntResponse.isSuccessful && sendGiftListCntResponse.isSuccessful) {
                var totalReceive = receiveGiftCntResponse.body()?.giftListTotalCount ?: 0
                var totalNotReceive = sendGiftListCntResponse.body()?.giftListTotalCount ?: 0
                totalCount.value = totalReceive + totalNotReceive
                if (totalReceive == 0)
                    totalReceive = 1
                if (totalNotReceive == 0)
                    totalNotReceive = 1
                if (totalCount.value == 0) {
                    totalCount.value = 1
                }
                val receiveGiftListResponse =
                    giftRepository.getGiftListAll(userId.toString(), 0, totalReceive, true)
                val sendGiftListResponse =
                    giftRepository.getGiftListAll(userId.toString(), 0, totalNotReceive, false)
                if (receiveGiftListResponse.isSuccessful && sendGiftListResponse.isSuccessful) {
                    getAllReceivedGiftListResponse.value = receiveGiftListResponse.body()
                    getAllNotReceivedGiftListResponse.value = sendGiftListResponse.body()
                    val tempSet = mutableSetOf<String>()
                    for (item in getAllReceivedGiftListResponse.value!!.giftListGifts) {
                        tempSet.add(item.giftName)
                    }
                    for (item in getAllNotReceivedGiftListResponse.value!!.giftListGifts) {
                        tempSet.add(item.giftName)
                    }
                    getAllName.value!!.addAll(tempSet)
                } else {
                    toast.postValue(NOTICE_FAIL_SEARCH)
                }
            } else {
                toast.postValue(NOTICE_FAIL_SEARCH)
            }
        }
    }

    companion object {
        private const val NOTICE_FAIL_SEARCH = "검색에 실패하였습니다"
    }
}