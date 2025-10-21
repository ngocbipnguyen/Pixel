package com.bachnn.feature.viewpager

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bachnn.data.model.User
import com.bachnn.feature.collection.navigation.CollectionRoute
import com.bachnn.feature.viewpager.screen.HomeScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Serializable
data class HomeRoute(val user: String?)

fun NavController.navigateToHome(user: User?, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    val json =
        user?.let { URLEncoder.encode(Json.encodeToString(it), StandardCharsets.UTF_8.toString()) }
    navigate(route = HomeRoute(json)) {
        navOptions()
    }
}

fun NavGraphBuilder.homeBuild(
    onClick: () -> Unit,
) {
    composable<HomeRoute> { user ->
        val route = user.toRoute<HomeRoute>()
        val user = route.user?.let {
            Json.decodeFromString<User>(URLDecoder.decode(it, "UTF-8"))
        }
        HomeScreen(user = user,onClick = onClick)
    }
}