package com.bachnn.feature.viewpager.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.Photographer
import com.bachnn.data.repository.FirstPhotographerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface InfoUiState {
    data class Success(
        val photographer: List<Photographer>
    ) : InfoUiState

    object Loading : InfoUiState
    data class Error(val error: String) : InfoUiState
}

@HiltViewModel
class InfoViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val collectionRepository: FirstPhotographerRepository
) : ViewModel() {

    var infoUiState: InfoUiState by mutableStateOf(InfoUiState.Loading)
        private set

    init {
        infoUiState = InfoUiState.Loading

        try {
            viewModelScope.launch {
                val collections = collectionRepository.getPhotographers()

                infoUiState = InfoUiState.Success(collections.take(3))
            }
        } catch (e: Exception) {
            infoUiState = InfoUiState.Error(e.message.toString())
        }

    }

}