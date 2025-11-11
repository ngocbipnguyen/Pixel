package com.bachnn.feature.viewpager.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.viewpager.screen.PhotographerScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class PhotographerRoute(val photographerSrc: String)

fun NavController.navigateToPhotographer(
    photographer: Photographer,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    val json = Json.encodeToString(photographer)
    navigate(route = PhotoDetailRoute(json)) {
        navOptions()
    }
}


fun NavGraphBuilder.buildPhotographer() {
    composable<PhotographerRoute> { backStackEntry ->
        val photographer: Photographer =
            Json.decodeFromString<Photographer>(backStackEntry.toRoute<PhotoDetailRoute>().pixelsPhotoSrc)
//        PhotoDetailScreen(photographer)
        PhotographerScreen(photographer)
    }
}
