package com.bachnn.core.network.model

import com.bachnn.core.database.model.PhotoSrcEntity
import com.bachnn.core.database.model.PixelsPhotoEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PixelsPhoto(
    val id: Long,
    @SerializedName("id_collection")
    val idCollection: String,
    val type: String,
    @SerializedName("photographer_id")
    val photographerId: Long,
    val photographer: String,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    val url: String,
    val width: Int,
    val height: Int,
    @SerializedName("avg_color")
    val avgColor: String,
    val src: PhotoSrc
)

fun PixelsPhoto.asExternalModel() = PixelsPhotoEntity(
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
    src = src.asExternalModel()
)

@Serializable
data class PhotoSrc(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

fun PhotoSrc.asExternalModel() = PhotoSrcEntity(
    original = original,
    large = large,
    large2x = large2x,
    medium = medium,
    small = small,
    portrait = portrait,
    landscape = landscape,
    tiny = tiny
)
