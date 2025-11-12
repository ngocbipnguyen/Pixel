package com.bachnn.data.model

import com.bachnn.core.database.model.PhotographerEntity
import com.bachnn.core.database.model.SocialMediaEntity
import com.bachnn.core.network.model.PhotographerNetwork
import java.util.ArrayList

data class Photographer(
    val id: String,
    val name: String,
    val url: String,
    val email: String,
    val timeCreate: Long,
    val timeLogin: Long,
    val token: String,
    val location: String,
    val totalView: Long,
    val allTimeRank: Long,
    val monthRank: Long,
    var follow: Boolean,
    val albums: List<Album>,
    val social: List<SocialMediaEntity>,
    val photos: List<PhotoSrc>
)

fun PhotographerNetwork.asExternalNetworkToDataModel(): Photographer {
    return Photographer(
        id = id,
        name = name,
        url = url,
        email = email,
        timeLogin = timeLogin,
        timeCreate = timeCreate,
        token = token,
        location = location,
        totalView = totalView,
        allTimeRank = allTimeRank,
        monthRank = monthRank,
        follow = follow,
        albums = albums.map { it -> it.asExternalNetworkToModel() },
        social = ArrayList<SocialMediaEntity>(),
        photos = photos.map { it -> it.asExternalNetworkToDataModel() }
    )
}


fun PhotographerNetwork.asExternalNetworkToEntity(): PhotographerEntity {
    return PhotographerEntity(
        id = id,
        name = name,
        url = url,
        email = email,
        timeLogin = timeLogin,
        timeCreate = timeCreate,
        token = token,
        location = location,
        totalView = totalView,
        allTimeRank = allTimeRank,
        monthRank = monthRank,
        follow = follow,
        albums = albums.map { it -> it.asExternalNetworkToEntity() },
        social = ArrayList<SocialMediaEntity>(),
        photos = photos.map { it -> it.asExternalNetworkToEntityModel() }
    )
}


fun PhotographerEntity.asExternalEntityToDataModel(): Photographer {
    return Photographer(
        id = id,
        name = name,
        url = url,
        email = email,
        timeLogin = timeLogin,
        timeCreate = timeCreate,
        token = token,
        location = location,
        totalView = totalView,
        allTimeRank = allTimeRank,
        monthRank = monthRank,
        follow = follow,
        albums = albums.map { it -> it.asExternalEntityToDataModel() },
        social = social,
        photos = photos.map { it -> it.asExternalNetworkToDataModel() }
    )
}


