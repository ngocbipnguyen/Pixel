package com.bachnn.core.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.data.repository.FirstPixelRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


sealed interface PixelUiState {
    data class Success(val pixels: List<PixelsPhoto>) : PixelUiState

    object Loading : PixelUiState

    data class Error(val error: String) : PixelUiState
}

@HiltViewModel
class PixelViewModel @AssistedInject constructor(
    val pixelRepository: FirstPixelRepository,
    @Assisted idCollection: String
) : ViewModel() {

    var pixelUiState: PixelUiState by mutableStateOf(PixelUiState.Loading)
        private set

    init {
        try {
            pixelUiState = PixelUiState.Loading
            viewModelScope.launch {
                val pixels = pixelRepository.getPhotosByIdCollection(idCollection)
                pixelUiState = PixelUiState.Success(pixels!!)
            }
        } catch (e: Exception) {
            pixelUiState = PixelUiState.Error(e.message!!)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(idCollection: String): PixelViewModel
    }

}