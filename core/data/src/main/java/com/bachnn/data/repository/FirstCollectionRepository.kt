package com.bachnn.data.repository

import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.model.CollectionWithPhotos
import com.bachnn.core.network.retrofit.NetworkRetrofit
import com.bachnn.data.model.Collection
import com.bachnn.data.model.asExternalEntityToDataModel
import com.bachnn.data.model.asExternalNetworkToDataModel
import com.bachnn.data.model.asExternalNetworkToEntityModel
import com.bachnn.data.model.asExternalToNetworkModel
import javax.inject.Inject

class FirstCollectionRepository @Inject constructor(
    private val collectionDao: CollectionDao,
    private val network: NetworkRetrofit
) : CollectionRepository {
    override suspend fun getCollections(): List<Collection> {
        var localTimestamp: Long? = collectionDao.getLatestTimestamp()
        if (localTimestamp == null) {
            localTimestamp = 0
        }
        val networkCollection = getCollectionsByTimestamps(localTimestamp)
        val collections = collectionDao.getCollections().map { it ->
            it.asExternalEntityToDataModel()
        }

       networkCollection.forEachIndexed { index, collection ->
           collections.forEach { it ->
               if (it.id == networkCollection[index].id) {
                   if (networkCollection[index].medias != null) {
                       it.medias =
                           networkCollection[index].medias.map { it -> it.asExternalNetworkToDataModel() }
                   }
               }
           }
       }

        return collections
    }
    suspend fun getCollectionsByTimestamps(timestamp: Long): List<com.bachnn.core.network.model.Collection> {
        val collections = network.getCollections()
        val networkTimestamp = collections.maxByOrNull { it.timestamps }
        if (networkTimestamp?.timestamps!! > timestamp) {
            val newCollections = collections.filter { it.timestamps > timestamp }
            collectionDao.insertCollections(newCollections.map { it -> it.asExternalNetworkToEntityModel() })
        }
        return collections
    }

    override suspend fun getCollection(id: String): Collection {
        return collectionDao.getCollectionById(id).asExternalEntityToDataModel()
    }

    override suspend fun getCollections(id: String): CollectionWithPhotos? {
        return collectionDao.getCollectionWithPhotos(id)
    }
}