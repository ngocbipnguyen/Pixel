package com.bachnn.data.model

import com.bachnn.core.database.model.CollectionEntity
import com.bachnn.core.network.model.Collection


data class Collection(
    val id: String,
    val title: String,
    val description: String,
    val isPrivate: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int,
    val timestamps: Long,
    var medias: List<PhotoSrc>
)

fun com.bachnn.core.network.model.Collection.asExternalNetworkToEntityModel(): CollectionEntity {
    return CollectionEntity(
        id = id,
        title = title,
        description = description,
        isPrivate = isPrivate,
        mediaCount = mediaCount,
        photosCount = photosCount,
        videosCount = videosCount,
        timestamps = timestamps
    )
}

fun Collection.asExternalToNetworkModel(): Collection {
    return Collection(
        id = id,
        title = title,
        description = description,
        isPrivate = isPrivate,
        mediaCount = mediaCount,
        photosCount = photosCount,
        videosCount = videosCount,
        timestamps = timestamps,
        medias = medias
    )
}

fun CollectionEntity.asExternalEntityToDataModel(): com.bachnn.data.model.Collection {
    return com.bachnn.data.model.Collection(
        id = id,
        title = title,
        description = description,
        isPrivate = isPrivate,
        mediaCount = mediaCount,
        photosCount = photosCount,
        videosCount = videosCount,
        timestamps = timestamps,
        medias = listOf()
    )
}