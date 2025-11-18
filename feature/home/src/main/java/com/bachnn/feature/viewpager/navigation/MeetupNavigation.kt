package com.bachnn.feature.viewpager.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bachnn.data.model.MeetUp
import com.bachnn.feature.viewpager.screen.MeetupDetailScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class MeetupRoute(val meetUp: String)

fun NavController.navigateToMeetup(meetup: MeetUp, navOption: NavOptionsBuilder.() -> Unit = {}) {
    val json = Json.encodeToString(meetup)
    navigate(MeetupRoute(json)) {
        navOption()
    }
}

fun NavGraphBuilder.buildMeetup(navController: NavController) {
    composable<MeetupRoute> { backStackEntry ->
        val meetup: MeetUp = Json.decodeFromString(backStackEntry.toRoute<MeetupRoute>().meetUp)
        MeetupDetailScreen(meetup)
    }
}