package com.bachnn.data.model

import com.bachnn.core.database.model.PixelsPhotoEntity
import com.bachnn.core.network.model.PixelsPhoto

data class PixelsPhoto(
    val id: Long,
    val idCollection: String,
    val type: String,
    val photographerId: Long,
    val photographer: String,
    val photographerUrl: String,
    val url: String,
    val width: Int,
    val height: Int,
    val avgColor: String,
    val src: PhotoSrc,
    val timestamps: Long,
)

fun PixelsPhoto.asExternalNetworkToEntityModel() = PixelsPhotoEntity(
    id = id,
    idCollection = idCollection,
    type = type,
    photographer = photographer,
    photographerId = photographerId,
    photographerUrl = photographerUrl,
    url = url,
    width = width,
    height = height,
    avgColor = avgColor,
    src = src.asExternalNetworkToEntityModel(),
    timestamps = timestamps
)

fun PixelsPhotoEntity.asExternalEntityToDataModel() = com.bachnn.data.model.PixelsPhoto(
    id = id,
    idCollection = idCollection,
    type = type,
    photographerId = photographerId,
    photographer = photographer,
    photographerUrl = photographerUrl,
    url = url,
    width = width,
    height = height,
    avgColor = avgColor,
    src = src.asExternalEntityToDataModel(),
    timestamps = timestamps
)

fun PixelsPhoto.asExternalNetworkToDataModel() = com.bachnn.data.model.PixelsPhoto(
    id = id,
    idCollection = idCollection,
    type = type,
    photographerId = photographerId,
    photographer = photographer,
    photographerUrl = photographerUrl,
    url = url,
    width = width,
    height = height,
    avgColor = avgColor,
    src = src.asExternalNetworkToDataModel(),
    timestamps = timestamps
)