package com.bachnn.pixel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bachnn.data.model.Collection
import com.bachnn.data.model.MeetUp
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.collection.navigation.collectionBuild
import com.bachnn.feature.viewpager.homeBuild
import com.bachnn.feature.viewpager.navigateToHome
import com.bachnn.feature.viewpager.navigation.buildCollectionDetail
import com.bachnn.feature.viewpager.navigation.buildPhotoDetail
import com.bachnn.feature.viewpager.navigation.buildPhotographer
import com.bachnn.feature.viewpager.navigation.navigateCollectionDetail
import com.bachnn.feature.viewpager.navigation.navigateToPhotoDetail
import com.bachnn.feature.viewpager.navigation.navigateToPhotographer

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
            onSplashClick = { isLogin, user ->
                if (isLogin) {
                    navHostController.navigateToHome(user) {
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

        loginBuild(onClick = { user ->
            navHostController.navigateToHome(user) {
                launchSingleTop = true
                popUpTo(LoginRoute) {
                    inclusive = true
                }
            }
        })

        homeBuild { action, data ->
            when (action) {
                1 -> {
                    //todo PhotoDetailScreen
                    if (data is PixelsPhoto) {
                        navHostController.navigateToPhotoDetail(data)
                    }
                }

                2 -> {
                    //todo PhotographerScreen
//                    if (data is Photographer) {
                    navHostController.navigateToPhotographer(data as Long)
//                    }
                }

                3 -> {
                    //todo CollectionScreen
                    if (data is Collection) {
                        // create a collection detail screen
                        navHostController.navigateCollectionDetail(data.id, data.title)
                    }
                }

                4 -> {
                    //todo Join Challenge Action
                }

                5 -> {
                    //todo PhotographerScreen do : update data later
                    navHostController.navigateToPhotographer(123456)
                }

                6 -> { //todo Follow Action

                }

                7 -> {//todo MeetupScreen
                    if (data is MeetUp) {
                        // create Meetup Screen
                    }
                }

                8 -> { // todo PhotoScreen
                    //
                    if (data is Collection) {
                        navHostController.navigateCollectionDetail(data.id, data.title)
                    }

                }


                9 -> { // todo create settings

                }

                10 -> { //todo Get Inspired Action

                }

                11 -> { //todo Favorite Action

                }

                12 -> { //todo Download Action

                }

                13 -> { //todo PhotographerScreen do : update data later
                    navHostController.navigateToPhotographer(123456)
                }

            }


        }

        buildPhotoDetail()

        buildPhotographer()

        buildCollectionDetail(navHostController)

        collectionBuild(
            onCollectionClick = {

            }
        ) {

        }
    }
}