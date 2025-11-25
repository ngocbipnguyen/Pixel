package com.bachnn.feature.viewpager.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.data.repository.FirstCollectionRepository
import com.bachnn.data.repository.FirstPhotographerRepository
import com.bachnn.data.repository.FirstPixelRepository
import com.bachnn.data.repository.PixelPhotoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


sealed interface ContentUiState {
    data class Success(
        val allPhotos: List<PixelsPhoto>
    ) : ContentUiState

    object Loading : ContentUiState
    data class Error(val error: String) : ContentUiState
}

@HiltViewModel(assistedFactory = ContentViewModel.Factory::class)
class ContentViewModel @AssistedInject constructor(
    val pixelPhotoRepository: FirstPixelRepository,
    @Assisted val photographer: Photographer
) : ViewModel() {

    var contentUiState: ContentUiState by mutableStateOf(ContentUiState.Loading)


    init {

        Log.e("ContentViewModel", photographer.id.toString())

        try {
            contentUiState = ContentUiState.Loading
            viewModelScope.launch {
                var allPhotos = ArrayList<PixelsPhoto>()
                photographer.albums.forEach { it ->
                    val photos = pixelPhotoRepository.getPhotosByCollectionId(it.id)
                    photos.let { c -> allPhotos.addAll(c) }
                    Log.e("ContentViewModel", "Photos size ${photos.size}")
                }
                contentUiState = ContentUiState.Success(allPhotos)
            }
        } catch (e: Exception) {
            contentUiState = ContentUiState.Error(e.message.toString())
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(photographer: Photographer): ContentViewModel
    }

}