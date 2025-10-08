package com.bachnn.data.repository

import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.model.CollectionEntity
import com.bachnn.core.database.model.CollectionWithPhotos
import com.bachnn.core.database.model.asExternalModel
import com.bachnn.core.network.model.asExternalModel
import com.bachnn.core.network.retrofit.NetworkRetrofit
import com.bachnn.data.Synchronizer
import com.bachnn.data.changeListSync
import com.bachnn.data.model.ChangeListVersions
import com.bachnn.data.model.Collection
import java.sql.Timestamp
import javax.inject.Inject

class FirstCollectionRepository @Inject constructor(
    private val collectionDao: CollectionDao,
    private val network: NetworkRetrofit
) : CollectionRepository {
    override suspend fun getCollections(): List<Collection> {
        val localTimestamp = collectionDao.getLatestTimestamp()
        getCollectionsByTimestamps(localTimestamp!!)
        return collectionDao.getCollections().map { it ->
            it.asExternalModel()
        }
    }
    suspend fun getCollectionsByTimestamps(timestamp: Long) {
        val collections = network.getCollections()
        val networkTimestamp = collections.maxByOrNull { it.timestamps }
        if (networkTimestamp?.timestamps!! > timestamp) {
            val newCollections = collections.filter { it.timestamps > timestamp }
            collectionDao.insertCollections(newCollections.map { it -> it.asExternalModel() })
        }
    }

    override suspend fun getCollection(id: String): Collection {
        return collectionDao.getCollectionById(id).asExternalModel()
    }

    override suspend fun getCollections(id: String): CollectionWithPhotos? {
        return collectionDao.getCollectionWithPhotos(id)
    }
}