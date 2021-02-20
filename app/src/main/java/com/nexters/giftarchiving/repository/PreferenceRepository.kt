package com.nexters.giftarchiving.repository

import android.content.Context

internal class PreferenceRepository(context: Context) {
    private val sf = context.getSharedPreferences(PREFERENCE_USER_KEY, Context.MODE_PRIVATE)

    fun setUserId(userId: Long) {
        with(sf.edit()) {
            putLong(PREFERENCE_USER_ID, userId)
            apply()
        }
    }

    fun getUserId() = sf.getLong(PREFERENCE_USER_ID, DEFAULT_VALUE_LONG)

    fun deleteUserId() {
        with(sf.edit()) {
            remove(PREFERENCE_USER_ID)
            apply()
        }
    }

    companion object {
        private const val PREFERENCE_USER_KEY = "user"
        private const val PREFERENCE_USER_ID = "user_id"

        private const val DEFAULT_VALUE_LONG = -1L
    }
}