package com.nexters.giftarchiving.util

import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.File

object ImageConverter {
    fun bitmapToFileConvert(
        bitmap: Bitmap,
        parentDir: File,
        fileName: String?
    ): File? {
        val file = File(parentDir, fileName ?: "gift_zip_${System.currentTimeMillis()}.png")
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val byteArray = bos.toByteArray()

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            fos.write(byteArray)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    fun fileToMultipartBody(file: File, multipartName: String?): MultipartBody.Part? {
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData(multipartName ?: "img", file.name, requestBody)
    }

    fun bitmapToMultipartBody(
        bitmap: Bitmap,
        parentDir: File,
        fileName: String?,
        multipartName: String?
    ): MultipartBody.Part? {
        return bitmapToFileConvert(bitmap, parentDir, fileName)?.let {
            fileToMultipartBody(it, multipartName)
        }
    }
}