package com.bachnn.core.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bachnn.core.database.local.dao.CollectionDao
import com.bachnn.core.database.local.dao.PhotoDao
import com.bachnn.core.database.model.PhotoSrcEntity
import com.bachnn.core.database.model.PixelsPhotoEntity
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
class PixelPhotoDaoTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }
    @Inject
    lateinit var photoDao: PhotoDao


    @Test
    fun insertPhoto() = runTest {
        val srcPhoto = PhotoSrcEntity(
            original = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg",
            large2x = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
            large = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
            medium = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&h=350",
            small = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&h=130",
            portrait = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
            landscape = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
            tiny = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
        )
        val photo = PixelsPhotoEntity(
            id = 29271144,
            idCollection = "mkuo3ci",
            type = "Photo",
            width = 2682,
            height = 4022,
            url = "https://www.pexels.com/photo/autumn-harvest-display-of-pumpkins-on-hay-29094998/",
            photographer = "Pierpaolo Riondato",
            photographerId = 831475,
            photographerUrl = "https://www.pexels.com/@rynocerontem",
            avgColor = "#674E38",
            src = srcPhoto,
            timestamps = 1759307221
        )

        photoDao.insertPixelPhoto(photo)

        val savePhoto = photoDao.getPixelPhotoById(29271144)

        assertEquals(photo.id, savePhoto.id)
        assertEquals(photo.idCollection, savePhoto.idCollection)
        assertEquals(photo.type, savePhoto.type)
    }

    @Test
    fun insertPhotos() = runTest {
        val srcPhoto = PhotoSrcEntity(
            original = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg",
            large2x = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
            large = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
            medium = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&h=350",
            small = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&h=130",
            portrait = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
            landscape = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
            tiny = "https://images.pexels.com/photos/29094998/pexels-photo-29094998.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
        )
        val photo = PixelsPhotoEntity(
            id = 29271144,
            idCollection = "mkuo3ci",
            type = "Photo",
            width = 2682,
            height = 4022,
            url = "https://www.pexels.com/photo/autumn-harvest-display-of-pumpkins-on-hay-29094998/",
            photographer = "Pierpaolo Riondato",
            photographerId = 831475,
            photographerUrl = "https://www.pexels.com/@rynocerontem",
            avgColor = "#674E38",
            src = srcPhoto,
            timestamps = 1759307221
        )

        val photos = listOf<PixelsPhotoEntity>(photo)

        photoDao.insertPixelPhotos(photos)

        val savePhotos = photoDao.getPixelPhotos().first()
        assertEquals(photo.id, savePhotos.id)
        assertEquals(photo.idCollection, savePhotos.idCollection)
        assertEquals(photo.type, savePhotos.type)
    }


}