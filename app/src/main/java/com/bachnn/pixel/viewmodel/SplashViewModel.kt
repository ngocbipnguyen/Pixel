package com.bachnn.pixel.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bachnn.data.model.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashUiState {
    data class Success(val isLogin: Boolean,val user: User?) : SplashUiState
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
            var user: User? = null
            if (auth.currentUser != null) {
                val uid = auth.currentUser?.uid.toString()
                val name = auth.currentUser?.displayName.toString()
                val email = auth.currentUser?.email.toString()
                val urlPhoto = auth.currentUser?.photoUrl.toString()
                user = User(uid, name, email, urlPhoto)
            }
            splashUiState = SplashUiState.Success(isLogin, user)
        }
    }

}