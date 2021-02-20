package com.nexters.giftarchiving.viewmodel

import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent


internal class AboutUsViewModel : BaseViewModel() {
    val clickMail = LiveEvent<Unit?>()

    fun onClickBack() {
        navDirections.value = BackDirections()
    }

    fun onClickMail(){
        clickMail.call()
    }
}