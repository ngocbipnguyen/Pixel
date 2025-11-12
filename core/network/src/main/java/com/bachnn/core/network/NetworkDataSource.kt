package com.bachnn.core.network

import com.bachnn.core.network.model.Collection
import com.bachnn.core.network.model.MeetUpNet
import com.bachnn.core.network.model.NetworkChangeList
import com.bachnn.core.network.model.PhotographerNetwork
import com.bachnn.core.network.model.PixelsPhoto

interface NetworkDataSource {

    suspend fun getCollections(): List<Collection>

    suspend fun getCollectionsById(id: String): Collection?

    suspend fun getMedias(): List<PixelsPhoto>

    suspend fun getMediasByIdCollection(idCollection: String): List<PixelsPhoto>?

    suspend fun getMediasById(id: Long): PixelsPhoto?

    suspend fun getCollectionChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getPhotographers(): List<PhotographerNetwork>

    suspend fun getPhotographer(id: String): PhotographerNetwork?

    suspend fun getMeetups(): List<MeetUpNet>

}