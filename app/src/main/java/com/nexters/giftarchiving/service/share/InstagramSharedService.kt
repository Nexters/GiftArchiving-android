package com.nexters.giftarchiving.service.share

import android.app.Activity
import android.content.Intent
import android.net.Uri

internal object InstagramSharedService {
    private const val INTENT_PACKAGE = "com.instagram.android"
    private const val INTENT_INSTAGRAM_STORY = "com.instagram.share.ADD_TO_STORY"
    private const val INTENT_INSTAGRAM_FEED = "com.instagram.share.ADD_TO_FEED"
    private const val DATA_TYPE = "image/*"
    private const val INSTAGRAM_STORY_BACKGROUND_COLOR = "#000000"

    private const val INSTAGRAM_STORY_REQUEST_CODE = 201
    private const val INSTAGRAM_FEED_REQUEST_CODE = 202

    fun shareInstagramStory(activity: Activity, uri: Uri) {
        val intent = Intent(INTENT_INSTAGRAM_STORY).apply {
            type = DATA_TYPE
            putExtra("interactive_asset_uri", uri)
            putExtra("top_background_color", INSTAGRAM_STORY_BACKGROUND_COLOR)
            putExtra("bottom_background_color", INSTAGRAM_STORY_BACKGROUND_COLOR)
        }

        activity.grantUriPermission(
            INTENT_PACKAGE, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        if (activity.packageManager.resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, INSTAGRAM_STORY_REQUEST_CODE)
        }
    }

    fun shareInstagramFeed(activity: Activity, uri: Uri) {
        val intent = Intent(INTENT_INSTAGRAM_FEED).apply {
            type = DATA_TYPE
            putExtra(Intent.EXTRA_STREAM, uri)
        }

        activity.grantUriPermission(
            INTENT_PACKAGE, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        if (activity.packageManager.resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, INSTAGRAM_FEED_REQUEST_CODE)
        }
    }
}