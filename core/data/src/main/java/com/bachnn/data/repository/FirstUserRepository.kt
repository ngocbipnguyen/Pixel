package com.bachnn.data.repository

import com.bachnn.core.database.local.dao.UserDao
import com.bachnn.core.database.model.UserEntity
import com.bachnn.data.model.User
import com.bachnn.data.model.asExternalDataToEntity
import com.bachnn.data.model.asExternalEntityToData
import javax.inject.Inject

class FirstUserRepository @Inject constructor(val userDao: UserDao): UserRepository {
    override suspend fun insertUser(userEntity: User) {
        userDao.insertUser(userEntity.asExternalDataToEntity())
    }

    override suspend fun getUserByUid(uid: String): User = userDao.getUser(uid).asExternalEntityToData()
}