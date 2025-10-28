package com.bachnn.data.model

import com.bachnn.core.network.model.PhotoSrc
import com.bachnn.core.network.model.PhotographerEntity

data class Photographer(
    val id: String,
    val name: String,
    val url: String,
    val email: String,
    val timeCreate: Long,
    val timeLogin: Long,
    val token: String,
    val photos: List<PhotoSrc>
)

fun PhotographerEntity.asExternalEntityToDataModel(): Photographer {
    return Photographer(
        id = id,
        name = name,
        url = url,
        email = email,
        timeLogin = timeLogin,
        timeCreate = timeCreate,
        token = token,
        photos = photos
    )
}


