package com.bachnn.pixel.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashUiState {
    data class Success(val isLogin: Boolean) : SplashUiState
    object Loading : SplashUiState
}

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    var splashUiState: SplashUiState by mutableStateOf(SplashUiState.Loading)
        private set

    init {
        splashUiState = SplashUiState.Loading
        viewModelScope.launch {
            delay(3000)
            val isLogin = auth.currentUser != null
            splashUiState = SplashUiState.Success(isLogin)
        }
    }

}