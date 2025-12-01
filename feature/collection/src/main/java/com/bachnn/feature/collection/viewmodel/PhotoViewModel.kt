package com.bachnn.feature.collection.viewmodel

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.data.repository.FirstPixelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import com.bachnn.feature.collection.task.loadImagesFromMediaStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File

sealed interface PixelUiState {
    data class Success(val pixels: List<PixelsPhoto>) : PixelUiState
    object Loading : PixelUiState
    data class Error(val error: String) : PixelUiState
}


@HiltViewModel(assistedFactory = PhotoViewModel.Factory::class)
class PhotoViewModel @AssistedInject constructor(
    val firstPixelRepository: FirstPixelRepository,
    @ApplicationContext context: Context,
    @Assisted val collectionId: String
) :
    ViewModel() {


    @AssistedFactory
    interface Factory {
        fun create(collectionId: String): PhotoViewModel
    }

    var pixelUiState: PixelUiState by mutableStateOf(PixelUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            pixelUiState = PixelUiState.Loading

            try {
                var photos: List<PixelsPhoto>? = null
                if (collectionId == "Favorite") {
                    photos = firstPixelRepository.getPhotosByFavorite()
                } else if (collectionId == "Mark") {
                    photos = firstPixelRepository.getPhotosByMark()
                } else if (collectionId == "Follow") {
                    photos = firstPixelRepository.getPhotosByFollow()
                } else if (collectionId == "Download") {
                    photos = loadImagesFromMediaStore(context)
                } else {
                    photos = (if (collectionId != "") {
                        firstPixelRepository.getPhotosByIdCollection(collectionId)
                    } else {
                        firstPixelRepository.getPhotos()
                    })
                }
                pixelUiState = if (photos == null) {
                    PixelUiState.Error("List Photo is null !")
                } else {
                    PixelUiState.Success(photos)
                }
            } catch (e: Exception) {
                pixelUiState = PixelUiState.Error(e.message.toString())
            }
        }
    }

    fun updateFavorite(id: Long, favorite: Boolean) {
        viewModelScope.launch {
            firstPixelRepository.updateFavorite(id, favorite)
        }
    }

    fun updateFollow(id: Long, follow: Boolean) {
        viewModelScope.launch {
            firstPixelRepository.updateFollow(id, follow)
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
