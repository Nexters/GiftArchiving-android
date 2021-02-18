package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.NoticeResponse
import com.nexters.giftarchiving.repository.NoticeRepository
import com.nexters.giftarchiving.ui.SettingsFragmentDirections
import com.nexters.giftarchiving.util.BackDirections
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val noticeRepository: NoticeRepository
) : BaseViewModel() {
    val notices = MutableLiveData(mutableListOf<NoticeResponse>())
    fun onClickBack() {
        navDirections.value = BackDirections()
    }
    fun onClickNotice(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToNoticeFragment()
        }
    }

    fun onClickTerms(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToTermsFragment()
        }
    }

    fun onClickOpensource(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToOpensourceFragment()
        }
    }
    fun onClickPrivacy(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToPrivacyFragment()
        }
    }
    init {
        viewModelScope.launch {
            val temp = mutableListOf<NoticeResponse>()
            temp.addAll(noticeRepository.getNoticeList().noticeList)
            notices.value = temp
        }
    }
}