package com.bachnn.core.network.remote

import com.bachnn.core.network.model.Collection
import com.bachnn.core.network.model.NetworkResponse
import com.bachnn.core.network.model.PixelsPhoto
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ApiInterfaceLocal {

    @GET("collections.json")
    suspend fun getCollections(): NetworkResponse<List<Collection>>

    @GET("medias.json")
    suspend fun getMedias(): NetworkResponse<List<PixelsPhoto>>

}