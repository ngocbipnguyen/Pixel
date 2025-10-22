package com.bachnn.feature.viewpager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bachnn.data.model.Collection
import com.bachnn.data.repository.FirstCollectionRepository
import com.bachnn.data.repository.FirstPixelRepository
import com.bachnn.feature.collection.viewmodel.CollectionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface FollowUiState {
    data class Success(val collection: List<Collection>) : FollowUiState
    object Loading : FollowUiState
    data class Error(val error: String) : FollowUiState
}

@HiltViewModel
class FollowViewModel @Inject constructor(
    val collectionRepository: FirstCollectionRepository,
    val pixelRepository: FirstPixelRepository
) : ViewModel() {
    var followUiState: FollowUiState by mutableStateOf(FollowUiState.Loading)
        private set

}