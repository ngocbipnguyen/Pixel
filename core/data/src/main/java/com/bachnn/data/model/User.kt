package com.bachnn.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    var token: String? = null,
)
