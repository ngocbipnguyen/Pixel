package com.bachnn.core.network.remote

import com.bachnn.core.network.model.Collection
import com.bachnn.core.network.model.NetworkResponse
import com.bachnn.core.network.model.PixelsPhoto
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ApiInterfaceLocal {

    @GET("collections.json")
    suspend fun getCollections(): List<Collection>

    @GET("collections.json")
    suspend fun getCollectionsById(id: String): Collection


    @GET("medias.json")
    suspend fun getMedias(): List<PixelsPhoto>

    @GET("medias.json")
    suspend fun getMediasByIdCollection(idCollection: String): List<PixelsPhoto>


    @GET("medias.json")
    suspend fun getMediasById(id: Long): PixelsPhoto
}