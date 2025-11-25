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
    @Query(
        value = """
            DELETE FROM pixels_photos
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteTopics(ids: List<String>)


    @Query("SELECT MAX(timestamps) FROM pixels_photos")
    suspend fun getLatestTimestamp(): Long?

    @Query("UPDATE pixels_photos SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE pixels_photos SET isMark = :isMark WHERE id = :id")
    suspend fun updateMark(id: Long, isMark: Boolean)

    @Query("UPDATE pixels_photos SET isFollow = :isFollow WHERE id = :id")
    suspend fun updateFollow(id: Long, isFollow: Boolean)


    @Query("SELECT * FROM pixels_photos WHERE idCollection= :collectionId")
    suspend fun getPhotosByCollectionId(collectionId: String): List<PixelsPhotoEntity>

}