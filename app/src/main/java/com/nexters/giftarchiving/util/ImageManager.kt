package com.nexters.giftarchiving.util

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

internal object ImageManager {

    fun saveImage(resolver: ContentResolver, bitmap: Bitmap): Uri? {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "gift_zip_${System.currentTimeMillis()}.png")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/GIFT_zip")
            }
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let {
                val fos = resolver.openOutputStream(it)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos?.flush()
                fos?.close()
                return it
            }
        }

        return null
    }
}