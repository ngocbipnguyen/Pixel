package com.bachnn.feature.viewpager.navigation

import androidx.compose.runtime.key
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.viewpager.screen.PhotographerScreen
import com.bachnn.feature.viewpager.viewmodel.PhotographerViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class PhotographerRoute(val photographerSrc: String)

fun NavController.navigateToPhotographer(
    photographer: Long,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    val json = photographer.toString()
    navigate(route = PhotographerRoute(json)) {
        navOptions()
    }
}


fun NavGraphBuilder.buildPhotographer(navController: NavController) {
    composable<PhotographerRoute> { backStackEntry ->
        val photographer: String = backStackEntry.toRoute<PhotographerRoute>().photographerSrc
        val viewModel: PhotographerViewModel =
            hiltViewModel<PhotographerViewModel, PhotographerViewModel.Factory>(
                key = photographer
            ) { factory ->
                factory.create(photographer)

            }
        PhotographerScreen(photographer, viewModel = viewModel, navigateHome = { action, data -> }, onBackPress = {navController.popBackStack()})
    }
}
