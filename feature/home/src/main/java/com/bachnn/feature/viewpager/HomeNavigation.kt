package com.bachnn.feature.viewpager

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.bachnn.feature.collection.navigation.CollectionRoute
import com.bachnn.feature.viewpager.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptionsBuilder.() -> Unit = {}){
    navigate(route = CollectionRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.homeBuild(
    onClick:() -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(onClick)
    }
}