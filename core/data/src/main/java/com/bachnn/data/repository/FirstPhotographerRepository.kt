package com.bachnn.data.repository

import com.bachnn.core.network.retrofit.NetworkRetrofit
import com.bachnn.data.model.MeetUp
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.asExternalEntityToDataModel
import javax.inject.Inject

class FirstPhotographerRepository @Inject constructor(val networkRetrofit: NetworkRetrofit) :
    PhotographerRepository {
    override suspend fun getPhotographers(): List<Photographer> {
        return networkRetrofit.getPhotographers().map { it -> it.asExternalEntityToDataModel() }
    }

    override suspend fun getPhotographer(id: String): Photographer? {
        return networkRetrofit.getPhotographer(id)?.asExternalEntityToDataModel()
    }

    override suspend fun getMeetUp(): List<MeetUp> {
        return networkRetrofit.getMeetups().map { it -> it.asExternalEntityToDataModel() }
    }
}