package com.bachnn.pixel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bachnn.feature.collection.navigation.collectionBuild
import com.bachnn.feature.viewpager.homeBuild
import com.bachnn.feature.viewpager.navigateToHome

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
            onSplashClick = { isLogin ->
                if (isLogin) {
                    navHostController.navigateToHome() {
                        launchSingleTop = true
                        popUpTo(SplashRoute) {
                            inclusive = true
                        }
                    }
                } else {
                    navHostController.navigateToLogin() {
                        launchSingleTop = true
                        popUpTo(SplashRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        )

        loginBuild(onClick = {
            navHostController.navigateToHome() {
                launchSingleTop = true
                popUpTo(LoginRoute) {
                    inclusive = true
                }
            }
        })

        homeBuild {  }

        collectionBuild(
            onCollectionClick = {

            }
        ) {

        }
    }
}