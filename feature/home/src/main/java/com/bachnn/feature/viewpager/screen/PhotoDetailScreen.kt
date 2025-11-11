package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bachnn.data.model.PixelsPhoto


@Composable
fun PhotoDetailScreen(pixelsPhoto: PixelsPhoto) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.error),
        contentAlignment = Alignment.Center
    ) {
        Text(pixelsPhoto.id.toString())
    }
}