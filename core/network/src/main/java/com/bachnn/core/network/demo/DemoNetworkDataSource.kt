package com.bachnn.core.network.demo

import com.bachnn.core.network.NetworkDataSource
import com.bachnn.core.network.model.Collection
import com.bachnn.core.network.model.MeetUpNet
import com.bachnn.core.network.model.NetworkChangeList
import com.bachnn.core.network.model.PhotographerEntity
import com.bachnn.core.network.model.PixelsPhoto
import com.bachnn.core.network.remote.ApiInterfaceLocal
import javax.inject.Inject

class DemoNetworkDataSource @Inject constructor(
    val apiInterfaceLocal: ApiInterfaceLocal
): NetworkDataSource {
    override suspend fun getCollections(): List<Collection> {
        return apiInterfaceLocal.getCollections()
    }

    override suspend fun getCollectionsById(id: String): Collection? {
        return apiInterfaceLocal.getCollections().find { it.id == id }
    }

    override suspend fun getMedias(): List<PixelsPhoto> {
        return apiInterfaceLocal.getMedias()
    }

    override suspend fun getMediasByIdCollection(idCollection: String): List<PixelsPhoto>? {
        return apiInterfaceLocal.getMedias().filter { it.idCollection == idCollection }
    }

    override suspend fun getMediasById(id: Long): PixelsPhoto? {
        return apiInterfaceLocal.getMedias().find { it.id == id }
    }

    override suspend fun getCollectionChangeList(after: Int?): List<NetworkChangeList> {
        TODO("Not yet implemented")
    }

    override suspend fun getPhotographers(): List<PhotographerEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getPhotographer(id: String): PhotographerEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getMeetups(): List<MeetUpNet> {
        TODO("Not yet implemented")
    }
}