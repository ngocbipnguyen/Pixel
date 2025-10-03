package com.bachnn.core.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.model.CollectionEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.test.assertEquals


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CollectionDaoTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Inject
    lateinit var collectionDao: CollectionDao

    @Test
    fun insertCollectionById() = runTest {
        val collection = CollectionEntity(
            id = "mkuo3ci",
            title = "Pumpkin Spice Season 🎃",
            description = "",
            isPrivate = false,
            mediaCount = 68,
            photosCount = 59,
            videosCount = 9,
            timestamps = 1759307220
        )

        collectionDao.insertCollection(collection)
        val collectionDb = collectionDao.getCollectionById("mkuo3ci")

        assertEquals(collection.id,collectionDb.id)
        assertEquals(collection.title,collectionDb.title)
        assertEquals(collection.description,collectionDb.description)
        assertEquals(collection.mediaCount,collectionDb.mediaCount)

    }


    @Test
    fun insertCollections() = runTest {
        val collections: List<CollectionEntity> =listOf<CollectionEntity>(
            CollectionEntity(
                id = "mkuo3ci",
                title = "Pumpkin Spice Season 🎃",
                description = "",
                isPrivate = false,
                mediaCount = 68,
                photosCount = 59,
                videosCount = 9,
                timestamps = 1759307220
            ),
            CollectionEntity(
                id = "mi3up33",
                title = "Autumn Desktop Wallpapers",
                description = "Bring the warmth of fall straight to your screen with Golden Days, a curated set of high-resolution desktop wallpapers inspired by everything we love about the season",
                isPrivate = false,
                mediaCount = 60,
                photosCount = 60,
                videosCount = 0,
                timestamps = 1759307210
            ),
            CollectionEntity(
                id = "vlhsvu3",
                title = "Aurora Gold",
                description = "A luminous celebration of golden hour’s fleeting beauty",
                isPrivate = false,
                mediaCount = 123,
                photosCount = 100,
                videosCount = 23,
                timestamps = 1759307200
            )
        )

        collectionDao.insertCollections(collections)
        val listCollections = collectionDao.getCollections()
        assertEquals(collections.size, listCollections.size)
    }

}