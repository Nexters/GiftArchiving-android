package com.nexters.giftarchiving.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections

class BackDirections(@IdRes val destinationId: Int = -1, val inclusive: Boolean = false) : NavDirections {
    override fun getArguments(): Bundle = Bundle()
    override fun getActionId(): Int = -1
}