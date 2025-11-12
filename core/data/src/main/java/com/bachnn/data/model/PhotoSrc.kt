package com.bachnn.data.model

import com.bachnn.core.database.model.PhotoSrcEntity
import com.bachnn.core.network.model.PhotoSrcNetwork
import kotlinx.serialization.Serializable

@Serializable
data class PhotoSrc(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)

fun PhotoSrcNetwork.asExternalNetworkToEntityModel() = PhotoSrcEntity(
    original = original,
    large = large,
    large2x = large2x,
    medium = medium,
    small = small,
    portrait = portrait,
    landscape = landscape,
    tiny = tiny
)

fun PhotoSrcEntity.asExternalNetworkToDataModel()= com.bachnn.data.model.PhotoSrc(
    original = original,
    large2x = large2x,
    large = large,
    medium = medium,
    small = small,
    portrait = portrait,
    landscape = landscape,
    tiny = tiny
)

fun PhotoSrcNetwork.asExternalNetworkToDataModel() = com.bachnn.data.model.PhotoSrc(
    original = original,
    large2x = large2x,
    large = large,
    medium = medium,
    small = small,
    portrait = portrait,
    landscape = landscape,
    tiny = tiny
)
