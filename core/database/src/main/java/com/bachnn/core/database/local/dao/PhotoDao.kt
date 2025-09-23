package com.bachnn.core.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bachnn.core.database.model.PixelsPhotoEntity

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPixelPhoto(pixelsPhoto: PixelsPhotoEntity)

    @Query("SELECT * FROM pixels_photos WHERE id= :idPhoto")
    suspend fun getPixelPhotoById(idPhoto: Int): PixelsPhotoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPixelPhotos(pixelPhotos: List<PixelsPhotoEntity>)

    @Query("SELECT* FROM pixels_photos")
    suspend fun getPixelPhotos(): List<PixelsPhotoEntity>

}