package com.nexters.giftarchiving.viewmodel

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

    fun onClickKakaoLogin() {
        kakaoLogin.call()
    }

    fun signIn(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = loginRepository.signIn(userId = userId.toString())
            if (response.isSuccess()) {
                preferenceRepository.setUserId(userId)
                navDirections.postValue(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        }
    }
}