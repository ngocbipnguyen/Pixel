package com.bachnn.core.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bachnn.core.database.converter.AlbumListConverter
import com.bachnn.core.database.converter.PhotoSrcListConvert
import com.bachnn.core.database.converter.SocialListConvert
import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.local.dao.PhotoDao
import com.bachnn.core.database.local.dao.PhotographerDao
import com.bachnn.core.database.local.dao.UserDao
import com.bachnn.core.database.model.AlbumEntity
import com.bachnn.core.database.model.CollectionEntity
import com.bachnn.core.database.model.CollectionWithPhotos
import com.bachnn.core.database.model.PhotoSrcEntity
import com.bachnn.core.database.model.PhotographerEntity
import com.bachnn.core.database.model.PixelsPhotoEntity
import com.bachnn.core.database.model.SocialMediaEntity
import com.bachnn.core.database.model.UserEntity

@Database(
    entities = [CollectionEntity::class, PixelsPhotoEntity::class, UserEntity::class, AlbumEntity::class, PhotographerEntity::class, SocialMediaEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    AlbumListConverter::class,
    SocialListConvert::class,
    PhotoSrcListConvert::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao

    abstract fun pixelPhotoDao(): PhotoDao

    abstract fun userDao(): UserDao

    abstract fun photographerDao(): PhotographerDao
}