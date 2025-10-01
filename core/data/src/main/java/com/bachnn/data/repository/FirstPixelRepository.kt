package com.bachnn.data.repository

import com.bachnn.core.database.local.dao.PhotoDao
import com.bachnn.core.database.model.asExternalModel
import com.bachnn.core.network.model.asExternalModel
import com.bachnn.core.network.retrofit.NetworkRetrofit
import com.bachnn.data.Synchronizer
import com.bachnn.data.changeListSync
import com.bachnn.data.model.ChangeListVersions
import com.bachnn.data.model.PixelsPhoto
import javax.inject.Inject

class FirstPixelRepository @Inject constructor(
    private val pixelDao: PhotoDao,
    private val networkRetrofit: NetworkRetrofit
): PixelPhotoRepository {
    override suspend fun getPhotos(): List<PixelsPhoto> {
        return pixelDao.getPixelPhotos().map { it -> it.asExternalModel() }
    }

    override suspend fun getPhoto(id: Int): PixelsPhoto {
        return pixelDao.getPixelPhotoById(id).asExternalModel()
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return synchronizer.changeListSync(
            versionReader = ChangeListVersions::topicVersion,
            changeListFetcher = { currentVersion ->
                networkRetrofit.getCollectionChangeList(currentVersion)
            },
            versionUpdater = {latestVersion ->
                copy(topicVersion = latestVersion)
            },
            modelDeleter = pixelDao::deleteTopics,
            modelUpdater = { id ->
                val collection = networkRetrofit.getMedias().map {
                        it -> it.asExternalModel()
                }
                pixelDao.insertPixelPhotos(collection)
            }
        )
    }
}