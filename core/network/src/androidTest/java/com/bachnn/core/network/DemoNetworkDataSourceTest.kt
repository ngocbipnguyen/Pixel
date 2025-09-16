package com.bachnn.core.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bachnn.core.network.demo.DemoNetworkDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DemoNetworkDataSourceTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var subject: DemoNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testDeserializationOfCollection() = runTest(testDispatcher) {
        val first = subject.getCollections().first()
        assertEquals("mkuo3ci", first.id)
        assertEquals("Pumpkin Spice Season 🎃", first.title)
        assertEquals(68, first.mediaCount)
        assertEquals(3, first.medias.size)
    }


    @Test
    fun testDeserializationOfCollectionById() = runTest(testDispatcher) {
        val collection = subject.getCollectionsById("mkuo3ci")
        assertEquals("Pumpkin Spice Season 🎃", collection?.title)
        assertEquals(68, collection?.mediaCount)
        assertEquals(3, collection?.medias?.size)
    }


    @Test
    fun testDeserializationOfMedia() = runTest(testDispatcher) {
        val first = subject.getMedias().first()
        assertEquals(30707095, first.id)
        assertEquals("Photo", first.type)
        assertEquals("https://images.pexels.com/photos/30707095/pexels-photo-30707095.jpeg", first.src.original)
    }

    @Test
    fun testDeserializationOfMediaByIdCollection() = runTest(testDispatcher) {
        val media = subject.getMediasByIdCollection("mkuo3ci")?.first()
        assertEquals(30707095, media?.id)
        assertEquals("Photo", media?.type)
    }
    @Test
    fun testDeserializationOfMediaById() = runTest(testDispatcher) {
        val media = subject.getMediasById(30707095
        )
        assertEquals(30707095, media?.id)
        assertEquals("Photo", media?.type)
    }




}