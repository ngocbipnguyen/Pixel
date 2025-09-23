package com.bachnn.core.database.model

import androidx.room.Entity

@Entity(
    tableName = "photo_src",
)
data class PhotoSrcEntity(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)