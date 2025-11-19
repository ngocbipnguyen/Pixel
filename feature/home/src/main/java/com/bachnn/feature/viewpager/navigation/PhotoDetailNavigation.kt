package com.bachnn.feature.viewpager.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.viewpager.HomeRoute
import com.bachnn.feature.viewpager.screen.PhotoDetailScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class PhotoDetailRoute(val pixelsPhotoSrc: String)

fun NavController.navigateToPhotoDetail(
    pixelsPhoto: PixelsPhoto,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    val json = Json.encodeToString(pixelsPhoto)
    navigate(route = PhotoDetailRoute(json)) {
        navOptions()
    }
}

fun NavGraphBuilder.buildPhotoDetail(navController: NavController) {
    composable<PhotoDetailRoute> { backStackEntry ->
        val pixelsPhoto: PixelsPhoto =
            Json.decodeFromString<PixelsPhoto>(backStackEntry.toRoute<PhotoDetailRoute>().pixelsPhotoSrc)
        PhotoDetailScreen(pixelsPhoto, onBackPress = {navController.popBackStack()})
    }
}
