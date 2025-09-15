package com.bachnn.core.network.retrofit

import com.bachnn.core.network.NetworkDataSource
import com.bachnn.core.network.model.Collection
import com.bachnn.core.network.model.PixelsPhoto
import com.bachnn.core.network.remote.ApiInterfaceLocal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRetrofit @Inject constructor(val apiInterfaceLocal: ApiInterfaceLocal): NetworkDataSource  {
    override suspend fun getCollections(): List<Collection> {
        return apiInterfaceLocal.getCollections().data
    }

    override suspend fun getMedias(): List<PixelsPhoto> {
        return apiInterfaceLocal.getMedias().data
    }
}