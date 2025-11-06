package com.bachnn.feature.viewpager.screen

import android.app.Activity
import android.os.Build
import android.view.WindowInsets
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bachnn.data.model.User
import com.bachnn.feature.collection.screen.CollectionScreen
import com.bachnn.feature.collection.screen.PhotoScreen
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
fun HomeScreen(user: User?, onClick: () -> Unit) {
    val pages = PixelPage.values()

    val context = LocalContext.current
    val activity = context as? Activity
    if (activity != null) {
        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)

        SideEffect {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.insetsController?.show(WindowInsets.Type.statusBars())
                } else {
                    WindowInsetsControllerCompat(
                        window,
                        window.decorView
                    ).show(WindowInsetsCompat.Type.statusBars())
                }
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

        }

        window.statusBarColor = MaterialTheme.colorScheme.primary.toArgb()

    }

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
fun HomePage(modifier: Modifier, user: User?, pages: Array<PixelPage>, onClick: () -> Unit) {
    var checked by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = {
        pages.size
    })
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f),
            userScrollEnabled = false
        ) { page ->
            when (pages[page]) {
                PixelPage.PHOTO_PAGE -> {
                    PhotoScreen(onclick = {

                    })
                }

                PixelPage.CHALLENGE_PAGE -> {
                    FollowScreen(onClick = {

                    })
                }

                PixelPage.ADD_PAGE -> {

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
            selectedTabIndex = pagerState.currentPage,
            indicator = {}
        ) {
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        if (index == 2) {
                            showDialog = true
                        } else {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = page.drawableResId),
                            contentDescription = title,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                        )
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.secondary,
                    selectedContentColor = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }


        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row {

                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(onClick = { showDialog = false }) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(MaterialTheme.shapes.extraLarge)
                                        .background(MaterialTheme.colorScheme.background),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Clear, contentDescription = "")
                                }
                            }
                        }


                        Spacer(Modifier.height(8.dp))

                        Text(
                            stringResource(R.string.confirm_media),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            stringResource(R.string.description_add_dialog),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(8.dp))

                        Row {

                            Checkbox(
                                checked = checked,
                                onCheckedChange = { checked = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colorScheme.onBackground,
                                )
                            )

                            Text(
                                stringResource(R.string.content_checkbox),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )

                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                showDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.inversePrimary
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                stringResource(R.string.upload_btn),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.height(46.dp))

                    }
                }
            }
        }


    }

}


