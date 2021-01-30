package com.nexters.giftarchiving.service.share

import android.app.Activity

import android.content.Intent
import android.net.Uri


internal object InstagramSharedService {
    fun shareInstagramStory(activity: Activity, uri : Uri) {
        val intent = Intent("com.instagram.share.ADD_TO_STORY").apply {
            setDataAndType(uri, "image/*")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        if (activity.packageManager.resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, 0)
        }
    }
}