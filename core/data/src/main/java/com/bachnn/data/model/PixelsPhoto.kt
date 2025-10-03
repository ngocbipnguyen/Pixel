package com.bachnn.data.model

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