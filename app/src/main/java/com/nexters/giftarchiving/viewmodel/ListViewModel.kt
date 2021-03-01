package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.GiftListResponse
import com.nexters.giftarchiving.repository.GiftRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.ui.ListFragmentArgs
import com.nexters.giftarchiving.ui.ListFragmentDirections
import com.nexters.giftarchiving.util.BackDirections
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ListViewModel(
    private val giftRepository: GiftRepository,
    private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {
    val listType = MutableLiveData<Boolean>()
    val title = MutableLiveData<String>()
    var isReceived = true
    var type = true
    var sort_text = MutableLiveData("최신순")
    val showSortBottom = MutableLiveData(false)
    val isLatest = MutableLiveData(true)
    val getAllReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    val getAllNotReceivedGiftListResponse = MutableLiveData(GiftListResponse(listOf(),0,0,0))
    var totalReceive = 0
    var totalNotReceive = 0
    val userId = preferenceRepository.getUserId()

    init {
        viewModelScope.launch {
            getAllList()
            navArgs<ListFragmentArgs>()
                .collect {
                    isReceived = it.title=="받은 선물"
                    title.value = it.title
                }
        }
    }

    val onClickListSwitchListener = View.OnClickListener(){
        onClickListSwitchButton()
    }

    private fun onClickListSwitchButton(){
        type = !type
        listType.value = type
    }

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun onClickSearch(){
        navDirections.value = ListFragmentDirections.actionListFragmentToSearchFragment()
    }

    fun onClickSortButton(){
        showSortBottom.value = true
    }

    fun onClickSortBackground(){
        showSortBottom.value = false
    }

    fun onClickSortLatest(){
        isLatest.value = true
        sort_text.value = "최신순"
        showSortBottom.value = false
    }

    fun onClickSortPast(){
        isLatest.value = false
        sort_text.value = "과거순"
        showSortBottom.value = false
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
                    listType.value = type
                    title.value = if (isReceived) String.format(
                        "%s %d",
                        title.value,
                        totalReceive
                    ) else String.format("%s %d", title.value, totalNotReceive)
                } else {
                    toast.postValue(NOTICE_FAIL_LOAD_LIST)
                }
            } else {
                toast.postValue(NOTICE_FAIL_LOAD_LIST)
            }
        }
    }

    fun onClickDetail(giftId: String) {
        navDirections.value = ListFragmentDirections.actionListFragmentToDetailFragment(giftId)
    }

    companion object {
        private const val NOTICE_FAIL_LOAD_LIST = "선물 리스트를 불러올 수 없습니다"
    }
}