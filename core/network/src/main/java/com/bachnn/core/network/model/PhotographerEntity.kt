package com.bachnn.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotographerEntity(
    val id: String,
    val name: String,
    val url: String,
    val email: String,
    val timeCreate: Long,
    val timeLogin: Long,
    val token: String,
    val photos: List<PhotoSrc>
)
