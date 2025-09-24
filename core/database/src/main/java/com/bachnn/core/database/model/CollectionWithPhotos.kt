package com.bachnn.core.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "CollectionWithPhotos")
data class CollectionWithPhotos(
    @Embedded val collection: CollectionEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "idCollection"
    )
    val photos: List<PixelsPhotoEntity>
)