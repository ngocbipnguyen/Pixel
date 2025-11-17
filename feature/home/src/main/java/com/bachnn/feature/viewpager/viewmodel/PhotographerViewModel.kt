package com.bachnn.feature.viewpager.viewmodel

import androidx.lifecycle.ViewModel
import com.bachnn.data.repository.FirstPhotographerRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = PhotographerViewModel.Factory::class)
class PhotographerViewModel @AssistedInject constructor(
    val photographerRepository: FirstPhotographerRepository,
    @Assisted val photographerId: String
): ViewModel() {

    init {

    }


    @AssistedFactory
    interface Factory {
        fun create(
            photographerId: String,
        ): PhotographerViewModel
    }
}