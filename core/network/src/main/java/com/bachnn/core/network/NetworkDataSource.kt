package com.bachnn.core.network

import com.bachnn.core.network.model.Collection
import com.bachnn.core.network.model.PixelsPhoto

interface NetworkDataSource {

    suspend fun getCollections(): List<Collection>

    suspend fun getMedias(): List<PixelsPhoto>

}