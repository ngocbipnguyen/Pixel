package com.bachnn.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PixelsPhoto(
    val id: Long,
    val type: String,
    val photographer_id: Long,
    val photographer: String,
    val photographer_url: String,
    val url: String,
    val width: Int,
    val height: Int,
    val avg_color: String,
    val src: PhotoSrc
)

@Serializable
data class PhotoSrc(
    val original: String,
    val medium: String,
    val small: String
)