package com.bachnn.feature.collection.viewmodel

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

sealed interface PixelUiState {
    data class Success(val pixels: List<PixelsPhoto>) : PixelUiState
    object Loading : PixelUiState
    data class Error(val error: String) : PixelUiState
}


@HiltViewModel
class PhotoViewModel @Inject constructor(val firstPixelRepository: FirstPixelRepository) :
    ViewModel() {

    var pixelUiState: PixelUiState by mutableStateOf(PixelUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            pixelUiState = PixelUiState.Loading
            try {
                val photos = firstPixelRepository.getPhotos()
                pixelUiState = PixelUiState.Success(photos)
            } catch (e: Exception) {
                pixelUiState = PixelUiState.Error(e.message.toString())
            }
        }
    }
}
