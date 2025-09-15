package com.bachnn.core.network.model

data class Collection(
    val id: String,
    val title: String,
    val description: String,
    val media_count: Int,
    val photos_count: Int,
    val videos_count: Int,
)