package com.bachnn.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bachnn.core.database.converter.AlbumListConverter
import com.bachnn.core.database.converter.PhotoSrcListConvert
import com.bachnn.core.database.converter.SocialListConvert

@Entity(tableName = "photographer")
data class PhotographerEntity(
    @PrimaryKey
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
    @TypeConverters(AlbumListConverter::class)
    val albums: List<AlbumEntity>,
    @TypeConverters(SocialListConvert::class)
    val social: List<SocialMediaEntity>,
    @TypeConverters(PhotoSrcListConvert::class)
    val photos: List<PhotoSrcEntity>
)
