package com.bachnn.feature.collection.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.Collection
import com.bachnn.data.repository.FirstCollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface CollectionUiState {
    data class Success(val collection: List<Collection>) : CollectionUiState
    object Loading : CollectionUiState
    data class Error(val error: String) : CollectionUiState
}

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionRepository: FirstCollectionRepository
) : ViewModel() {

    var collectionUiState: CollectionUiState by mutableStateOf(CollectionUiState.Loading)
        private set


    init {
        try {
            collectionUiState = CollectionUiState.Loading

            viewModelScope.launch {
                val collections = collectionRepository.getCollections()
                collectionUiState = CollectionUiState.Success(collections)
            }
        } catch (e: Exception) {
            collectionUiState = CollectionUiState.Error(e.message!!)
        }
    }

}