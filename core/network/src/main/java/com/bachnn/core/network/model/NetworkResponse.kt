package com.bachnn.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val data: T,
)