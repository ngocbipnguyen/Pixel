package com.bachnn.data.repository

import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.local.dao.PhotoDao
import com.bachnn.core.network.retrofit.NetworkRetrofit
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.data.model.asExternalEntityToDataModel
import com.bachnn.data.model.asExternalNetworkToEntityModel
import javax.inject.Inject

class FirstPixelRepository @Inject constructor(
    private val pixelDao: PhotoDao,
    private val collectionDao: CollectionDao,
    private val networkRetrofit: NetworkRetrofit
): PixelPhotoRepository {
    override suspend fun getPhotos(): List<PixelsPhoto> {
        var localTimestamp: Long? = pixelDao.getLatestTimestamp()
        if (localTimestamp == null) {
            localTimestamp = 0
        }
        getPhotosByTimestamps(localTimestamp)
        return pixelDao.getPixelPhotos().map { it -> it.asExternalEntityToDataModel() }
    }

    override suspend fun getPhotosByIdCollection(idCollection: String): List<PixelsPhoto>? {
        return collectionDao.getCollectionWithPhotos(idCollection)?.photos?.map { it -> it.asExternalEntityToDataModel() }
    }


    suspend fun getPhotosByTimestamps(timestamp: Long) {
        val photos = networkRetrofit.getMedias()
        val networkTimestamp = photos.maxByOrNull { it.timestamps }
        if (networkTimestamp?.timestamps!! > timestamp) {
            val updatePhotos = photos.filter { it.timestamps > timestamp }
            pixelDao.insertPixelPhotos(updatePhotos.map { it -> it.asExternalNetworkToEntityModel() })
        }
    }

    override suspend fun getPhoto(id: Int): PixelsPhoto {
        return pixelDao.getPixelPhotoById(id).asExternalEntityToDataModel()
    }

}