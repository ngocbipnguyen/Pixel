package com.bachnn.feature.viewpager.viewmodel

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.data.repository.FirstCollectionRepository
import com.bachnn.data.repository.FirstPixelRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

sealed interface MarkUiState {
    data class Success(
        val allPhotos: List<PixelsPhoto>
    ) : MarkUiState

    object Loading : MarkUiState
    data class Error(val error: String) : MarkUiState
}

@HiltViewModel(assistedFactory = MarkViewModel.Factory::class)
class MarkViewModel @AssistedInject constructor(
    val pixelRepository: FirstPixelRepository,
    val collectionRepository: FirstCollectionRepository,
    @Assisted val photographer: Photographer
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(photographer: Photographer): MarkViewModel
    }

    var markUiState: MarkUiState by mutableStateOf(MarkUiState.Loading)

    init {
        // todo: add collection favorite, mark, download, and album in photographer.
        viewModelScope.launch {
//            val pixelFavorite = pixelRepository.getPhotosByFavorite()
//            val pixelMark = pixelRepository.getPhotosByMark()
//            val pixelFollow = pixelRepository.getPhotosByFollow()
//            val uriDownload =  loadImagesFromMediaStore()

            markUiState = MarkUiState.Loading
            try {
                val pixels = ArrayList<PixelsPhoto>()
                photographer.albums.forEach { it ->
                    val collection = pixelRepository.getPhotosByCollectionId(it.id)
                    pixels.add(collection[0])
                }
                markUiState = MarkUiState.Success(pixels)
            } catch (e: Exception) {
                markUiState = MarkUiState.Error(e.message.toString())
            }

        }
    }


    @SuppressLint("Range")
    fun loadImagesFromMediaStore(context: Context): List<Uri> {
        val images = mutableListOf<Uri>()

        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.RELATIVE_PATH
        )

        val selection = "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("Download/pixel/%")

        val cursor = context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            MediaStore.Images.Media.DATE_ADDED + " DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndex(MediaStore.Images.Media._ID)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                )
                images.add(uri)
            }
        }

        return images
    }


}