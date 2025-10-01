package com.bachnn.data.repository

import com.bachnn.data.Syncable
import com.bachnn.data.Synchronizer
import com.bachnn.data.model.Collection
import kotlinx.coroutines.flow.Flow

interface CollectionRepository : Syncable {

    suspend fun getCollections(): List<Collection>

    suspend fun getCollection(id: String): Collection

}