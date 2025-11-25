package com.bachnn.data.repository

import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.local.dao.PhotoDao
import com.bachnn.core.database.model.PixelsPhotoEntity
import com.bachnn.core.network.retrofit.NetworkRetrofit
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.data.model.asExternalNetworkToDataModel
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
        return pixelDao.getPixelPhotos().map { it -> it.asExternalNetworkToDataModel() }
    }

    override suspend fun getPhotosByIdCollection(idCollection: String): List<PixelsPhoto>? {
        return collectionDao.getCollectionWithPhotos(idCollection)?.photos?.map { it -> it.asExternalNetworkToDataModel() }
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
        return pixelDao.getPixelPhotoById(id).asExternalNetworkToDataModel()
    }

    override suspend fun updateFavorite(id: Long, isFavorite: Boolean) {
        return pixelDao.updateFavorite(id, isFavorite)
    }

    override suspend fun updateMark(id: Long, isMark: Boolean) {
        return pixelDao.updateMark(id, isMark)
    }

    override suspend fun updateFollow(id: Long, isFollow: Boolean) {
        return pixelDao.updateFollow(id, isFollow)
    }

    override suspend fun getPhotosByCollectionId(collectionId: String): List<PixelsPhoto> {
        return pixelDao.getPhotosByCollectionId(collectionId = collectionId).map { it -> it.asExternalNetworkToDataModel() }
    }

}