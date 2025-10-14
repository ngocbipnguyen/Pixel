package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed interface AuthorizationUiState {
    //    data class Success(val collection: List<>) : AuthorizationUiState
    object Loading : AuthorizationUiState
    data class Error(val error: String) : AuthorizationUiState
}

@Composable
fun HomeScreen(onClick: () -> Unit) {
    Scaffold { contentPadding ->
        HomePage(modifier = Modifier.padding(top = contentPadding.calculateTopPadding()))
    }
}

@Composable
fun HomePage(modifier: Modifier) {

}


