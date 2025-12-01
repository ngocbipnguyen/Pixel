package com.bachnn.feature.collection.task

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.bachnn.data.model.PhotoSrc
import com.bachnn.data.model.PixelsPhoto


@SuppressLint("Range")
fun loadImagesFromMediaStore(context: Context): List<PixelsPhoto> {
    val photos = mutableListOf<PixelsPhoto>()

    val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.RELATIVE_PATH,
        MediaStore.Images.Media.WIDTH,
        MediaStore.Images.Media.HEIGHT,
        MediaStore.Images.Media.DATE_ADDED,
        MediaStore.Images.Media.DATE_TAKEN
    )

    val selection = "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
    val selectionArgs = arrayOf("Download/pixel/%")

    val cursor = context.contentResolver.query(
        collection,
        projection,
        selection,
        selectionArgs,
        MediaStore.Images.Media.DATE_ADDED + " DESC"
    )

    cursor?.use {
        val idColumn = it.getColumnIndex(MediaStore.Images.Media._ID)
        val widthColumn = it.getColumnIndex(MediaStore.Images.Media.WIDTH)
        val heightColumn = it.getColumnIndex(MediaStore.Images.Media.HEIGHT)
        val dateAddedColumn = it.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)
        val dateTakenColumn = it.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)
        val displayNameColumn = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

        while (it.moveToNext()) {
            val id = it.getLong(idColumn)
            val width = it.getInt(widthColumn)
            val height = it.getInt(heightColumn)
            val dateAdded =
                it.getLong(dateAddedColumn) * 1000
            val dateTaken = if (dateTakenColumn >= 0) {
                it.getLong(dateTakenColumn)
            } else {
                dateAdded // Fallback to dateAdded
            }
            val displayName = it.getString(displayNameColumn) ?: "image_$id"

            val uri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
            )

            val pixelPhoto = createPixelsPhotoFromUri(
                uri = uri,
                id = id,
                width = width,
                height = height,
                timestamp = dateTaken,
                displayName = displayName
            )

            photos.add(pixelPhoto)
        }
    }

    Log.e("MarkViewModel","download size: ${photos.size}")
    return photos
}


private fun createPixelsPhotoFromUri(
    uri: Uri,
    id: Long,
    width: Int,
    height: Int,
    timestamp: Long,
    displayName: String
): PixelsPhoto {
    val uriString = uri.toString()

    val photoSrc = PhotoSrc(
        original = uriString,
        large2x = uriString,
        large = uriString,
        medium = uriString,
        small = uriString,
        portrait = uriString,
        landscape = uriString,
        tiny = uriString
    )

    val avgColor = "#000000"

    return PixelsPhoto(
        id = id,
        idCollection = "local_downloads",
        type = "photo",
        photographerId = 0L,
        photographer = "Download",
        photographerUrl = "",
        url = uriString,
        width = if (width > 0) width else 1920,
        height = if (height > 0) height else 1080,
        avgColor = avgColor,
        src = photoSrc,
        timestamps = timestamp,
        isFavorite = false,
        isFollow = false,
        isMark = true
    )
}