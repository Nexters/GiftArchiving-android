package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.repository.LoginRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.ui.LoginFragmentDirections
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {
    val kakaoLogin = LiveEvent<Unit?>()
    val isLoading = MutableLiveData<Boolean>()

    fun onClickKakaoLogin() {
        kakaoLogin.call()
    }

    fun signIn(userId: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = loginRepository.signIn(userId = userId.toString())
            if (response.code() == SUCCESS_SIGN_UP_CODE || response.code() == SUCCESS_SIGN_IN_CODE) {
                preferenceRepository.setUserId(userId)
                isLoading.postValue(false)
                navDirections.postValue(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        }
    }

    companion object {
        private const val SUCCESS_SIGN_UP_CODE = 200
        private const val SUCCESS_SIGN_IN_CODE = 406
    }
}