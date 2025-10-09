package com.bachnn.pixel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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
        navHostController, startDestination = CollectionRoute
    ) {
        collectionBuild(
            onCollectionClick = {

            }
        ) {

        }
    }
}