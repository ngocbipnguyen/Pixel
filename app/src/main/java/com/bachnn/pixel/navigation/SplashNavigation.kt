package com.bachnn.pixel.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.bachnn.data.model.User
import com.bachnn.feature.collection.navigation.CollectionRoute
import com.bachnn.pixel.screen.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

fun NavController.navigateToCollection(navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = SplashRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.splashBuild(
    onSplashClick:(Boolean, User?) -> Unit,
) {
    composable<SplashRoute> {
        SplashScreen(onSplashClick)
    }
}