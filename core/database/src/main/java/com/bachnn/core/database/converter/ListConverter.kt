package com.bachnn.core.database.converter

import androidx.room.TypeConverter
import com.bachnn.core.database.model.AlbumEntity
import com.bachnn.core.database.model.PhotoSrcEntity
import com.bachnn.core.database.model.SocialMediaEntity
import kotlinx.serialization.json.Json


object AlbumListConverter {
    @TypeConverter
    fun fromAlbumList(list: List<AlbumEntity>): String = Json.encodeToString(list)

    @TypeConverter
    fun toAlbumList(json: String): List<AlbumEntity> = Json.decodeFromString(json)
}

object SocialListConvert {
    @TypeConverter
    fun fromList(list: List<SocialMediaEntity>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(json: String): List<SocialMediaEntity> = Json.decodeFromString(json)
}


object PhotoSrcListConvert {
    @TypeConverter
    fun fromList(list: List<PhotoSrcEntity>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(json: String): List<PhotoSrcEntity> = Json.decodeFromString(json)
}