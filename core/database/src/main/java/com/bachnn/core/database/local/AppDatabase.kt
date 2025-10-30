package com.bachnn.core.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.local.dao.PhotoDao
import com.bachnn.core.database.local.dao.UserDao
import com.bachnn.core.database.model.CollectionEntity
import com.bachnn.core.database.model.CollectionWithPhotos
import com.bachnn.core.database.model.PhotoSrcEntity
import com.bachnn.core.database.model.PixelsPhotoEntity
import com.bachnn.core.database.model.UserEntity

@Database(
    entities = [CollectionEntity::class, PixelsPhotoEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao

    abstract fun pixelPhotoDao(): PhotoDao

    abstract fun userDao(): UserDao
}