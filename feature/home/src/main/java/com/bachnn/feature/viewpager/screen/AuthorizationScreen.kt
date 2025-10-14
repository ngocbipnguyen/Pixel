package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AuthorizationScreen(onClick: () -> Unit) {
    Scaffold { paddingValues ->
        AuthorizationPage(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            onClick
        )
    }
}

@Composable
fun AuthorizationPage(modifier: Modifier, onClick: () -> Unit) {

}

