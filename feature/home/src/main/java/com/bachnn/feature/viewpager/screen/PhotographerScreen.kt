package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.viewpager.viewmodel.PhotographerViewModel

@Composable
fun PhotographerScreen(photographerId: String, viewModel: PhotographerViewModel = hiltViewModel()) {
    PhotographerPage(photographerId)

}

@Composable
fun PhotographerPage(photographerId: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.error),
        contentAlignment = Alignment.Center
    ) {
        Text(photographerId)
    }
}