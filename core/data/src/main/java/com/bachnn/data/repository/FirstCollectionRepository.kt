package com.bachnn.data.repository

import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.model.asExternalModel
import com.bachnn.core.network.model.asExternalModel
import com.bachnn.core.network.retrofit.NetworkRetrofit
import com.bachnn.data.Synchronizer
import com.bachnn.data.changeListSync
import com.bachnn.data.model.ChangeListVersions
import com.bachnn.data.model.Collection
import javax.inject.Inject

class FirstCollectionRepository @Inject constructor(
    private val collectionDao: CollectionDao,
    private val network: NetworkRetrofit
) : CollectionRepository {
    override suspend fun getCollections(): List<Collection> {
        return collectionDao.getCollections().map { it ->
            it.asExternalModel()
        }
    }

    override suspend fun getCollection(id: String): Collection {
        return collectionDao.getCollectionById(id).asExternalModel()
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return synchronizer.changeListSync(
            versionReader = ChangeListVersions::topicVersion,
            changeListFetcher = { currentVersion ->
                network.getCollectionChangeList(currentVersion)
            },
            versionUpdater = {latestVersion ->
                copy(topicVersion = latestVersion)
            },
            modelDeleter = collectionDao::deleteTopics,
            modelUpdater = { id ->
                val collection = network.getCollections().map {
                    it -> it.asExternalModel()
                }
                collectionDao.insertCollections(collection)
            }
        )
    }
}