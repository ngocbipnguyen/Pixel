package com.bachnn.data.repository

import com.bachnn.core.database.model.UserEntity
import com.bachnn.data.model.User

interface UserRepository {

    suspend fun insertUser(userEntity: User)

    suspend fun getUserByUid(uid: String): User

}