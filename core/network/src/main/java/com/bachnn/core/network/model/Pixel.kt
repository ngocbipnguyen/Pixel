package com.bachnn.core.network.model

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
    val src: PhotoSrcNetwork,
    @SerializedName("timestamp")
    val timestamps: Long
)

@Serializable
data class PhotoSrcNetwork(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
