package com.bachnn.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "photo_src",
)
@Serializable
data class PhotoSrcEntity(
    @PrimaryKey
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

//fun PhotoSrcEntity.asExternalModel() = PhotoSrc(
//    original = original,
//    large2x = large2x,
//    large = large,
//    medium = medium,
//    small = small,
//    portrait = portrait,
//    landscape = landscape,
//    tiny = tiny
//)