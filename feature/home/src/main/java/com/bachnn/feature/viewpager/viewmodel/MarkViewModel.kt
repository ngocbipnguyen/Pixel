package com.bachnn.feature.viewpager.viewmodel

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.PhotoSrc
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.data.model.User
import com.bachnn.data.repository.FirstPixelRepository
import com.bachnn.feature.collection.task.loadImagesFromMediaStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlin.Long

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
    @ApplicationContext private val context: Context,
    @Assisted val photographer: Photographer?,
    @Assisted val user: User?
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(photographer: Photographer?, user: User?): MarkViewModel
    }

    var markUiState: MarkUiState by mutableStateOf(MarkUiState.Loading)

    init {
        viewModelScope.launch {

            markUiState = MarkUiState.Loading
            try {
                val pixels = ArrayList<PixelsPhoto>()

                if (user != null) {
                    val pixelFavorite = pixelRepository.getPhotosByFavorite()
                    val pixelMark = pixelRepository.getPhotosByMark()
                    val pixelFollow = pixelRepository.getPhotosByFollow()
                    val uriDownload = loadImagesFromMediaStore(context)
                    Log.e("MarkViewModel", "pixelFollow size: ${pixelFollow.size}")
                    if (pixelFavorite.isNotEmpty()) {
                        var favorite = pixelFavorite[0]
                        favorite.photographer = "Favorite"
                        pixels.add(favorite)
                    }
                    if (pixelMark.isNotEmpty()) {
                        var mark = pixelMark[0]
                        mark.photographer = "Mark"
                        pixels.add(mark)
                    }
                    if (pixelMark.isNotEmpty()) {
                        var follow = pixelFollow[0]
                        follow.photographer = "Follow"
                        pixels.add(follow)
                    }
                    if (uriDownload.isNotEmpty()) {
                        pixels.add(uriDownload[0])
                    }
                }
                photographer?.albums?.forEach { it ->
                    val collection = pixelRepository.getPhotosByCollectionId(it.id)
                    pixels.add(collection[0])
                }
                markUiState = MarkUiState.Success(pixels)
            } catch (e: Exception) {
                markUiState = MarkUiState.Error(e.message.toString())
            }

        }
    }

}