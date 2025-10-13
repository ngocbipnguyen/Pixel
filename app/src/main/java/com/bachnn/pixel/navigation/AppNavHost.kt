package com.bachnn.pixel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bachnn.feature.collection.navigation.CollectionRoute
import com.bachnn.feature.collection.navigation.collectionBuild

@Composable
fun NavApp() {
    val navController = rememberNavController()
    NavAppHost(navController)
}

@Composable
fun NavAppHost(navHostController: NavHostController) {
    NavHost(
        navHostController, startDestination = SplashRoute
    ) {

        splashBuild(
            onSplashClick = {
                navHostController.navigateToCollection() {
                    launchSingleTop = true
                    popUpTo(SplashRoute) {
                        inclusive = true
                    }
                }
            }
        )
        collectionBuild(
            onCollectionClick = {

            }
        ) {

        }
    }
}