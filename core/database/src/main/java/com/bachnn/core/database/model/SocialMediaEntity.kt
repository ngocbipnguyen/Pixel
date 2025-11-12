package com.bachnn.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "social_media")
data class SocialMediaEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val icon: Long,
    val link: String,
    var enable: Boolean
)