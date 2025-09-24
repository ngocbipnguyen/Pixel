package com.bachnn.core.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.local.dao.PhotoDao
import com.bachnn.core.database.model.CollectionEntity
import com.bachnn.core.database.model.CollectionWithPhotos
import com.bachnn.core.database.model.PhotoSrcEntity
import com.bachnn.core.database.model.PixelsPhotoEntity

@Database(
    entities = [CollectionEntity::class, PixelsPhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao

    abstract fun pixelPhotoDao(): PhotoDao
}