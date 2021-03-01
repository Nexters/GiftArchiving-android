package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.model.NoticeResponse
import com.nexters.giftarchiving.repository.NoticeRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.ui.SettingsFragmentDirections
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val noticeRepository: NoticeRepository,
    private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {
    val notices = MutableLiveData(mutableListOf<NoticeResponse>())
    val clickLogout = LiveEvent<Unit?>()

    fun onClickBack() {
        navDirections.value = BackDirections()
    }
    fun onClickNotice(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToNoticeFragment()
        }
    }

    fun onClickAboutUs() {
        viewModelScope.launch {
            navDirections.value = SettingsFragmentDirections.actionSettingsFragmentToAboutUsFragment()
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

    fun onClickLogout() {
        clickLogout.call()
    }

    fun logout() {
        UserApiClient.instance.logout { error ->
            if(error != null) toast.value = NOTICE_FAIL_LOGOUT
            else {
                preferenceRepository.deleteUserId()
                navDirections.value =
                    SettingsFragmentDirections.actionSettingsFragmentToSplashFragment()
            }
        }
    }

    init {
        viewModelScope.launch {
            val noticeResponse = noticeRepository.getNoticeList()
            if (noticeResponse.isSuccessful) {
                noticeRepository.getNoticeList().body()?.noticeList?.let {
                    val temp = mutableListOf<NoticeResponse>()
                    temp.addAll(it)
                    notices.postValue(temp)
                }
            }
        }
    }

    companion object {
        private const val NOTICE_FAIL_LOGOUT = "로그아웃 실패"
    }
}