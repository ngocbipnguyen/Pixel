package com.bachnn.data.repository

import com.bachnn.data.Syncable
import com.bachnn.data.model.PixelsPhoto

interface PixelPhotoRepository {

    suspend fun getPhotos(): List<PixelsPhoto>

    suspend fun getPhoto(id: Int): PixelsPhoto

}