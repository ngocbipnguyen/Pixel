package com.bachnn.feature.viewpager.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bachnn.feature.viewpager.screen.SettingsDetailScreen
import kotlinx.serialization.Serializable


@Serializable
data class SettingsRoute(val uid: String)

fun NavController.navigateToSettings(uid: String, navOption: NavOptionsBuilder.() -> Unit = {}) {
    navigate(SettingsRoute(uid)) {
        navOption()
    }
}

fun NavGraphBuilder.buildSettings(navController: NavController) {
    composable<SettingsRoute> { backStackEntry ->
        val uid = backStackEntry.toRoute<SettingsRoute>().uid
        SettingsDetailScreen(uid)
    }
}


