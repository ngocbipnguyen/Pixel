package com.bachnn.core.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("private")
    val isPrivate: Boolean,
    @SerializedName("media_count")
    val mediaCount: Int,
    @SerializedName("photos_count")
    val photosCount: Int,
    @SerializedName("videos_count")
    val videosCount: Int,
    val medias: List<PhotoSrcNetwork>,
    @SerializedName("timestamp")
    val timestamps: Long
)

//fun Collection.asExternalModel(): CollectionEntity {
//
//    return CollectionEntity(
//        id = id,
//        title = title,
//        description = description,
//        isPrivate = isPrivate,
//        mediaCount = mediaCount,
//        photosCount = photosCount,
//        videosCount = videosCount,
//        timestamps = timestamps
//    )
//
//}