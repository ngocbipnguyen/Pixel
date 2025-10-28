package com.bachnn.data.repository

import com.bachnn.core.network.model.PhotographerEntity
import com.bachnn.data.model.MeetUp
import com.bachnn.data.model.Photographer

interface PhotographerRepository {

    suspend fun getPhotographers(): List<Photographer>

    suspend fun getPhotographer(id: String): Photographer?

    suspend fun getMeetUp(): List<MeetUp>
}