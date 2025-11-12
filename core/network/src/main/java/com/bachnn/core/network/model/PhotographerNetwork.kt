package com.bachnn.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotographerNetwork(
    val id: String,
    val name: String,
    val url: String,
    val email: String,
    val timeCreate: Long,
    val timeLogin: Long,
    val token: String,
    val location: String,
    val totalView: Long,
    val allTimeRank: Long,
    val monthRank: Long,
    val follow: Boolean,
    val albums: List<AlbumNetwork>,
    val photos: List<PhotoSrcNetwork>
)
