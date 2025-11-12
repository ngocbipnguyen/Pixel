package com.bachnn.core.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bachnn.core.database.model.PhotographerEntity
import com.bachnn.core.database.model.SocialMediaEntity

@Dao
interface PhotographerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotographer(photographerEntity: PhotographerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotographers(photographerEntities: List<PhotographerEntity>)

    @Query("SELECT * FROM photographer WHERE id= :id")
    suspend fun getPhotographerById(id: String): PhotographerEntity

    @Query("SELECT* FROM photographer")
    suspend fun getPhotographers(): List<PhotographerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSocialMedia(socialMediaEntity: SocialMediaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSocialMedias(socialMediaEntities: List<SocialMediaEntity>)

    @Query("SELECT * FROM social_media WHERE id= :id")
    suspend fun getSocialMediaById(id: String): SocialMediaEntity

    @Query("UPDATE social_media SET name = :name WHERE id = :id")
    suspend fun updateNameSocialMedia(id: String, name: String)

    @Query("UPDATE social_media SET icon = :icon WHERE id = :id")
    suspend fun updateIconSocialMedia(id: String, icon: Long)

    @Query("UPDATE social_media SET link = :link WHERE id = :id")
    suspend fun updateLinkSocialMedia(id: String, link: String)

    @Query("UPDATE social_media SET enable = :enable WHERE id = :id")
    suspend fun updateEnableSocialMedia(id: String, enable: Boolean)

}