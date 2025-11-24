package com.bachnn.feature.viewpager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.Photographer
import com.bachnn.data.repository.FirstPhotographerRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


sealed interface PhotographerUiState {
    data class Success(
        val photographer: Photographer
    ) : PhotographerUiState

    object Loading : PhotographerUiState
    data class Error(val error: String) : PhotographerUiState
}


@HiltViewModel(assistedFactory = PhotographerViewModel.Factory::class)
class PhotographerViewModel @AssistedInject constructor(
    val photographerRepository: FirstPhotographerRepository,
    @Assisted val photographerId: String
) : ViewModel() {

    var photographerUiState: PhotographerUiState by mutableStateOf(PhotographerUiState.Loading)


    init {
        try {
            photographerUiState = PhotographerUiState.Loading
            viewModelScope.launch {
                val photographer = photographerRepository.getPhotographer(photographerId)
                if (photographer == null) {
                    photographerUiState = PhotographerUiState.Error("Photographer is null.")
                } else {
                    photographerUiState = PhotographerUiState.Success(photographer)
                }
            }
        } catch (e: Exception) {
            photographerUiState = PhotographerUiState.Error(e.message.toString())
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(
            photographerId: String,
        ): PhotographerViewModel
    }
}