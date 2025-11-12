package com.bachnn.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "album")
data class AlbumEntity(
    @PrimaryKey
    val id: String
)
