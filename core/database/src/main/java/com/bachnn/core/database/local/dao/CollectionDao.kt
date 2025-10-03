package com.bachnn.core.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bachnn.core.database.model.CollectionEntity
import com.bachnn.core.database.model.CollectionWithPhotos
import com.bachnn.core.database.model.PixelsPhotoEntity


@Dao
interface CollectionDao {

    // Collection
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity)

    @Query("SELECT * FROM collections WHERE id = :collectionId")
    suspend fun getCollectionById(collectionId: String): CollectionEntity

    @Query("SELECT * FROM collections")
    suspend fun getCollections(): List<CollectionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollections(collections: List<CollectionEntity>)

    @Transaction
    @Query("SELECT * FROM collections WHERE id = :collectionId")
    suspend fun getCollectionWithPhotos(collectionId: String): CollectionWithPhotos?

    @Transaction
    @Query("SELECT * FROM collections")
    suspend fun getAllCollectionsWithPhotos(): List<CollectionWithPhotos>

    @Query(
        value = """
            DELETE FROM collections
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteTopics(ids: List<String>)

    @Query("SELECT MAX(timestamps) FROM collections")
    suspend fun getLatestTimestamp(): Long?

}