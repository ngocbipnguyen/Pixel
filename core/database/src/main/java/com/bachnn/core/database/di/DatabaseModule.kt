package com.bachnn.core.database.di

import android.content.Context
import androidx.room.Room
import com.bachnn.core.database.local.AppDatabase
import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.local.dao.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java, "collection_db")
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideCollectionDao(appDatabase: AppDatabase): CollectionDao {
        return appDatabase.collectionDao()
    }

    @Provides
    @Singleton
    fun providePixelPhotoDao(appDatabase: AppDatabase): PhotoDao{
        return  appDatabase.pixelPhotoDao()
    }


}