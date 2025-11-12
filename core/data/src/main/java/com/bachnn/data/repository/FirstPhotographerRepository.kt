package com.bachnn.data.repository

import com.bachnn.core.database.local.dao.PhotographerDao
import com.bachnn.core.network.model.PhotographerNetwork
import com.bachnn.core.network.retrofit.NetworkRetrofit
import com.bachnn.data.model.MeetUp
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.asExternalEntityToDataModel
import com.bachnn.data.model.asExternalNetworkToDataModel
import com.bachnn.data.model.asExternalNetworkToEntity
import javax.inject.Inject

class FirstPhotographerRepository @Inject constructor(
    val networkRetrofit: NetworkRetrofit,
    val photographerDao: PhotographerDao
) :
    PhotographerRepository {
    override suspend fun getPhotographers(): List<Photographer> {
        val photographerEntity = photographerDao.getPhotographers()
        if (photographerEntity.isEmpty()) {
            val photographerNetwork = networkRetrofit.getPhotographers()
            photographerDao.insertPhotographers(photographerNetwork.map { it -> it.asExternalNetworkToEntity() })
            return photographerNetwork.map { it -> it.asExternalNetworkToDataModel() }
        }
        return photographerEntity.map { it -> it.asExternalEntityToDataModel() }
    }

    override suspend fun getPhotographer(id: String): Photographer? {
        val photographer = photographerDao.getPhotographerById(id)
        if (photographer != null) {
            val photographerNetwork: PhotographerNetwork = networkRetrofit.getPhotographer(id)!!
            photographerDao.insertPhotographer(photographerNetwork.asExternalNetworkToEntity())
            return photographerNetwork.asExternalNetworkToDataModel()
        }
        return networkRetrofit.getPhotographer(id)?.asExternalNetworkToDataModel()
    }

    override suspend fun getMeetUp(): List<MeetUp> {
        return networkRetrofit.getMeetups().map { it -> it.asExternalNetworkToDataModel() }
    }
}