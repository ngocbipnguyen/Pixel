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
        val localTimestamp = pixelDao.getLatestTimestamp()
        getPhotosByTimestamps(localTimestamp!!)
        return pixelDao.getPixelPhotos().map { it -> it.asExternalModel() }
    }

    suspend fun getPhotosByTimestamps(timestamp: Long) {
        val photos = networkRetrofit.getMedias()
        val networkTimestamp = photos.maxByOrNull { it.timestamps }
        if (networkTimestamp?.timestamps!! > timestamp) {
            val updatePhotos = photos.filter { it.timestamps > timestamp }
            pixelDao.insertPixelPhotos(updatePhotos.map { it -> it.asExternalModel() })
        }
    }

    override suspend fun getPhoto(id: Int): PixelsPhoto {
        return pixelDao.getPixelPhotoById(id).asExternalModel()
    }

}