package com.bachnn.data.repository

import com.bachnn.core.database.model.PixelsPhotoEntity
import com.bachnn.data.Syncable
import com.bachnn.data.model.PixelsPhoto

interface PixelPhotoRepository {

    suspend fun getPhotos(): List<PixelsPhoto>

    suspend fun getPhotosByIdCollection(idCollection: String): List<PixelsPhoto>?

    suspend fun getPhoto(id: Int): PixelsPhoto

    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    suspend fun updateMark(id: Long, isMark: Boolean)

    suspend fun updateFollow(id: Long, isFavorite: Boolean)

    suspend fun getPhotosByCollectionId(collectionId: String): List<PixelsPhoto>

}