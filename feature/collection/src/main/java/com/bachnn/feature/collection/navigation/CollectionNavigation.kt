package com.bachnn.feature.collection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bachnn.data.model.Collection
import com.bachnn.feature.collection.screen.CollectionScreen
import kotlinx.serialization.Serializable


@Serializable
data object CollectionRoute

//@Serializable
//data object CollectionBaseRoute

fun NavController.navigateCollection(navOptions: NavOptions) {
    return navigate(route = CollectionRoute, navOptions)
}

fun NavGraphBuilder.collectionBuild(
    onCollectionClick:(Collection) -> Unit,
    collectionDestination: NavGraphBuilder.() -> Unit
) {
    composable<CollectionRoute> {
        CollectionScreen(onCollectionClick)
    }
}