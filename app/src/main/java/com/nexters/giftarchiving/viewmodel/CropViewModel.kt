package com.nexters.giftarchiving.viewmodel

import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.LiveEvent

internal class CropViewModel : BaseViewModel() {
    val crop = LiveEvent<Unit?>()

    fun onClickBack(){
        navDirections.value = BackDirections()
    }

    fun onCrop() {
        crop.call()
    }
}