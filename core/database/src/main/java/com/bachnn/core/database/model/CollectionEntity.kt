package com.bachnn.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class CollectionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val isPrivate: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int,
    val timestamps: Long
)

//fun CollectionEntity.asExternalModel(): Collection {
//
//    return Collection(
//        id = id,
//        title = title,
//        description = description,
//        isPrivate = isPrivate,
//        mediaCount = mediaCount,
//        photosCount = photosCount,
//        videosCount = videosCount,
//        timestamps = timestamps,
//        medias = listOf()
//    )
//
//}