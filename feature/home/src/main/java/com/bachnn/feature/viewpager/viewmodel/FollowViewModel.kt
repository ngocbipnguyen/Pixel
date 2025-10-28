package com.bachnn.feature.viewpager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.Collection
import com.bachnn.data.model.MeetUp
import com.bachnn.data.model.Photographer
import com.bachnn.data.repository.FirstCollectionRepository
import com.bachnn.data.repository.FirstPhotographerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FollowUiState {
    data class Success(
        val collections: List<Collection>,
        val photographers: List<Photographer>,
        val meetup: List<MeetUp>
    ) : FollowUiState

    object Loading : FollowUiState
    data class Error(val error: String) : FollowUiState
}

@HiltViewModel
class FollowViewModel @Inject constructor(
    val collectionRepository: FirstCollectionRepository,
    val photographerRepository: FirstPhotographerRepository
) : ViewModel() {
    var followUiState: FollowUiState by mutableStateOf(FollowUiState.Loading)
        private set

    init {
        try {
            followUiState = FollowUiState.Loading
            viewModelScope.launch {
                val collections = collectionRepository.getCollections()
                val photographers = photographerRepository.getPhotographers()
                val meetup = photographerRepository.getMeetUp()
                followUiState = FollowUiState.Success(collections, photographers, meetup)
            }
        } catch (e: Exception) {
            followUiState = FollowUiState.Error(e.message.toString())
        }
    }

}