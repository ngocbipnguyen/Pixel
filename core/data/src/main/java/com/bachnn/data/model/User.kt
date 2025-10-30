package com.bachnn.data.model

import com.bachnn.core.database.model.UserEntity
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    var token: String? = null,
)

fun User.asExternalDataToEntity(): UserEntity {
    return UserEntity(
        uid = uid,
        name = name,
        email = email,
        photoUrl = photoUrl,
        token = token
    )
}

fun UserEntity.asExternalEntityToData(): User {
    return User(
        uid = uid,
        name = name,
        email = email,
        photoUrl = photoUrl,
        token = token
    )
}
