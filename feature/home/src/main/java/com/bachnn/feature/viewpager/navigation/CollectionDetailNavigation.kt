package com.bachnn.feature.viewpager.navigation

import androidx.compose.runtime.key
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bachnn.feature.collection.PHOTOGRAPHER
import com.bachnn.feature.collection.PHOTO_DETAIL
import com.bachnn.feature.collection.screen.PhotoScreen
import com.bachnn.feature.collection.viewmodel.PhotoViewModel
import kotlinx.serialization.Serializable


@Serializable
data class CollectionDetailRoute(val collectionId: String, val collectionName: String)

fun NavController.navigateCollectionDetail(
    collectionId: String,
    collectionName: String,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = CollectionDetailRoute(collectionId, collectionName)) {
        navOptions()
    }
}

fun NavGraphBuilder.buildCollectionDetail(navController: NavController, navigateHome: (Int, Any) -> Unit) {
    composable<CollectionDetailRoute> { backStackEntry ->
        val collectionId = backStackEntry.toRoute<CollectionDetailRoute>().collectionId
        val collectionName = backStackEntry.toRoute<CollectionDetailRoute>().collectionName
        val viewModel =
            hiltViewModel<PhotoViewModel, PhotoViewModel.Factory>(key = collectionId) { factory ->
                factory.create(collectionId)
            }
        PhotoScreen(
            onClickPhoto = { pixel ->
                navigateHome(PHOTO_DETAIL, pixel)
            },
            onClickPhotographer = { photographerId ->
                navigateHome(PHOTOGRAPHER, photographerId)
            },
            onBack = { navController.popBackStack() },
            viewModel = viewModel,
            collectionId = collectionId,
            nameCollection = collectionName)
    }
}