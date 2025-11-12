package com.bachnn.data.model

import com.bachnn.core.database.model.AlbumEntity
import com.bachnn.core.network.model.AlbumNetwork
import kotlinx.serialization.Serializable

@Serializable
data class Album(val id: String)

fun AlbumNetwork.asExternalNetworkToEntityModel(): AlbumEntity {
    return AlbumEntity(
        id = id
    )
}


fun AlbumNetwork.asExternalNetworkToModel(): Album {
    return Album(
        id = id
    )
}


fun AlbumNetwork.asExternalNetworkToEntity(): AlbumEntity {
    return AlbumEntity(
        id = id
    )
}


fun AlbumEntity.asExternalEntityToDataModel(): Album {
    return Album(
        id = id
    )
}
