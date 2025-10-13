package com.bachnn.core.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pixels_photos")
data class PixelsPhotoEntity(
    @PrimaryKey val id: Long,
    val idCollection: String,  // foreign key tới Collection
    val type: String,
    val photographerId: Long,
    val photographer: String,
    val photographerUrl: String,
    val url: String,
    val width: Int,
    val height: Int,
    val avgColor: String,
    val timestamps: Long,
    @Embedded val src: PhotoSrcEntity  // nhúng PhotoSrc
)

//fun PixelsPhotoEntity.asExternalModel(): PixelsPhoto {
//    return PixelsPhoto(
//        id = id,
//        idCollection = idCollection,
//        type = type,
//        photographerId = photographerId,
//        photographer = photographer,
//        photographerUrl = photographerUrl,
//        url = url,
//        width = width,
//        height = height,
//        avgColor = avgColor,
//        src = src.asExternalModel(),
//        timestamps = timestamps
//    )
//}