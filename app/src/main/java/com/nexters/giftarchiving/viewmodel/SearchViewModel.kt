package com.nexters.giftarchiving.viewmodel

import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.util.BackDirections

internal class SearchViewModel: BaseViewModel(){
    fun onClickBack() {
        navDirections.value = BackDirections()
    }
}