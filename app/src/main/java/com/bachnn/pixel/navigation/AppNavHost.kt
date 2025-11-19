package com.bachnn.pixel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bachnn.data.model.Collection
import com.bachnn.data.model.MeetUp
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.collection.CHALLENGE_ACTION
import com.bachnn.feature.collection.COLLECTION_DETAIL
import com.bachnn.feature.collection.DOWNLOAD_ACTION
import com.bachnn.feature.collection.FAVORITE_ACTION
import com.bachnn.feature.collection.FOLLOW_ACTION
import com.bachnn.feature.collection.INSPIRED_ACTION
import com.bachnn.feature.collection.MEETUP
import com.bachnn.feature.collection.PHOTOGRAPHER
import com.bachnn.feature.collection.PHOTO_DETAIL
import com.bachnn.feature.collection.SETTINGS
import com.bachnn.feature.collection.navigation.collectionBuild
import com.bachnn.feature.viewpager.homeBuild
import com.bachnn.feature.viewpager.navigateToHome
import com.bachnn.feature.viewpager.navigation.buildCollectionDetail
import com.bachnn.feature.viewpager.navigation.buildMeetup
import com.bachnn.feature.viewpager.navigation.buildPhotoDetail
import com.bachnn.feature.viewpager.navigation.buildPhotographer
import com.bachnn.feature.viewpager.navigation.buildSettings
import com.bachnn.feature.viewpager.navigation.navigateCollectionDetail
import com.bachnn.feature.viewpager.navigation.navigateToMeetup
import com.bachnn.feature.viewpager.navigation.navigateToPhotoDetail
import com.bachnn.feature.viewpager.navigation.navigateToPhotographer
import com.bachnn.feature.viewpager.navigation.navigateToSettings

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
                PHOTO_DETAIL -> {
                    if (data is PixelsPhoto) {
                        navHostController.navigateToPhotoDetail(data)
                    }
                }

                PHOTOGRAPHER -> {

                    val photographerId: Long = if (data is String) {
                         data.toLong()
                    } else  {
                        data as Long
                    }

                    navHostController.navigateToPhotographer(photographerId)
                }

                COLLECTION_DETAIL -> {
                    if (data is Collection) {
                        // create a collection detail screen
                        navHostController.navigateCollectionDetail(data.id, data.title)
                    }
                }

                CHALLENGE_ACTION -> {
                    //todo Join Challenge Action
                }

//                PHOTOGRAPHER -> {
//                    navHostController.navigateToPhotographer(123456)
//                }

                FOLLOW_ACTION -> { //todo Follow Action

                }

                MEETUP -> {
                    if (data is MeetUp) {
                        navHostController.navigateToMeetup(data)
                    }
                }

//                COLLECTION_DETAIL -> {
//                    if (data is Collection) {
//                        navHostController.navigateCollectionDetail(data.id, data.title)
//                    }
//                }


                SETTINGS -> {
                    navHostController.navigateToSettings(data as String)
                }

                INSPIRED_ACTION -> { //todo Get Inspired Action

                }

                FAVORITE_ACTION -> { //todo Favorite Action

                }

                DOWNLOAD_ACTION -> { //todo Download Action

                }

//                PHOTOGRAPHER -> {
//                    navHostController.navigateToPhotographer(123456)
//                }

            }


        }

        buildPhotoDetail(navHostController)

        buildPhotographer()

        buildCollectionDetail(navHostController)

        buildSettings(navHostController)

        buildMeetup(navHostController)

        collectionBuild(
            onCollectionClick = {

            }
        ) {

        }
    }
}