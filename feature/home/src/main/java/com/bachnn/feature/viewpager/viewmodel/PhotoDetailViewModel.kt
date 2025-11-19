package com.bachnn.feature.viewpager.viewmodel

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.core.database.local.dao.PhotoDao
import com.bachnn.data.repository.FirstPixelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(val firstPixelRepository: FirstPixelRepository,): ViewModel() {

    init {

    }

    fun updateFavorite(id: Long, favorite: Boolean) {
        viewModelScope.launch {
            firstPixelRepository.updateFavorite(id, favorite)
        }
    }

    fun updateMark(id: Long, isMark: Boolean) {
        viewModelScope.launch {
            firstPixelRepository.updateMark(id, isMark)
        }
    }

    fun download(context: Context, original: String) {
        val pixelFile = File(Environment.DIRECTORY_DOWNLOADS, "pixel")
        if (!pixelFile.exists()) {
            pixelFile.mkdirs()
        }
        val namePhoto = original.substringAfterLast('/')
        val request = DownloadManager.Request(original.toUri())
            .setTitle(namePhoto)
            .setTitle("Downloading image ...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "pixel/$namePhoto")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val download = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        download.enqueue(request)
    }

}