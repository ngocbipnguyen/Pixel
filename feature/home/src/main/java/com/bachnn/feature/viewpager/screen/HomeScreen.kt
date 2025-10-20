package com.bachnn.feature.viewpager.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bachnn.data.model.User
import com.bachnn.feature.collection.CollectionScreen
import com.bachnn.feature.viewpager.R
import kotlinx.coroutines.launch

sealed interface AuthorizationUiState {
    //    data class Success(val collection: List<>) : AuthorizationUiState
    object Loading : AuthorizationUiState
    data class Error(val error: String) : AuthorizationUiState
}

enum class PixelPage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    PHOTO_PAGE(R.string.title_photo, R.drawable.baseline_home_24),
    CHALLENGE_PAGE(R.string.title_challenge, R.drawable.outline_add_alert_24),
    ADD_PAGE(R.string.title_add, R.drawable.outline_add_circle_24),
    SEARCH_PAGE(R.string.title_search, R.drawable.outline_youtube_searched_for_24),
    SETTINGS_PAGE(R.string.title_setting, R.drawable.sharp_settings_24)
}

@Composable
fun HomeScreen(user: User, onClick: () -> Unit) {
    val pages = PixelPage.values()
    Scaffold { contentPadding ->
        HomePage(
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
            user = user,
            pages,
            onClick
        )
    }
}

@Composable
fun HomePage(modifier: Modifier, user: User, pages: Array<PixelPage>, onClick: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = {
        5
    })
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) { page ->
            when (pages[page]) {
                PixelPage.PHOTO_PAGE -> {

                }

                PixelPage.CHALLENGE_PAGE -> {
                    FollowScreen(onClick = {

                    })
                }

                PixelPage.ADD_PAGE -> {
                    AddScreen { }
                }

                PixelPage.SEARCH_PAGE -> {
                    CollectionScreen(onClickCollection = {

                    })
                }

                PixelPage.SETTINGS_PAGE -> {
                    SettingsScreen { }
                }
            }
        }

        // Tab Row
        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    icon = {
                        Icon(
                            painter = painterResource(id = page.drawableResId),
                            contentDescription = title
                        )
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }

}


