package com.bachnn.pixel.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.bachnn.pixel.screen.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = LoginRoute) {
        navOptions()
    }
}


fun NavGraphBuilder.loginBuild(onClick:() -> Unit) {
    composable<LoginRoute>{
        LoginScreen(onClick)
    }
}