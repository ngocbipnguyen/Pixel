package com.bachnn.data.model

import com.bachnn.core.database.model.SocialMediaEntity
import kotlinx.serialization.Serializable

@Serializable
data class SocialMedia(
    val id: String,
    val name: String,
    val icon: Int,
    val link: String,
    var enable: Boolean,
    val photographerId: Long
)

fun SocialMediaEntity.asExternalEntityToDataModel(): SocialMedia {
    return SocialMedia(
        id = id,
        name = name,
        icon = icon,
        link = link,
        enable = enable,
        photographerId = photographerId
    )
}


fun SocialMedia.asExternalDataToEntityModel(): SocialMediaEntity {
    return SocialMediaEntity(
        id = id,
        name = name,
        icon = icon,
        link = link,
        enable = enable,
        photographerId = photographerId
    )
}

