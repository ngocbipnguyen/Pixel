package com.bachnn.feature.viewpager.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.User
import com.bachnn.feature.collection.SETTINGS
import com.bachnn.feature.collection.screen.PagerError
import com.bachnn.feature.collection.screen.PagerLoading
import com.bachnn.feature.collection.view.CircleNetworkImage
import com.bachnn.feature.viewpager.R
import com.bachnn.feature.viewpager.viewmodel.ContentViewModel
import com.bachnn.feature.viewpager.viewmodel.FollowUiState
import com.bachnn.feature.viewpager.viewmodel.MarkViewModel
import com.bachnn.feature.viewpager.viewmodel.SettingsUiState
import com.bachnn.feature.viewpager.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch


enum class SettingsPage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    PHOTO(R.string.photo_tag, R.drawable.outline_photo_library_24),
    MARK(R.string.mark_tag, R.drawable.outline_bookmark_24),
    INFO(R.string.info_tag, R.drawable.outline_circles_24)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateHome: (Int, Any) -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()

    var user: User? = null

    BottomSheetScaffold(
        topBar = {
            TopAppBar(
                title = { }, actions = {
                    IconButton(onClick = {
                        if (user != null) {
                            navigateHome(SETTINGS, user!!.uid)
                        } else {
                            navigateHome(SETTINGS, "")
                        }
                    }) {
                        Icon(Icons.Default.Settings, contentDescription = "Share")
                    }
                })
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 400.dp,
        sheetContent = {

            val pages = SettingsPage.entries.toTypedArray()
            val pagerState = rememberPagerState(pageCount = {
                pages.size
            })
            val coroutineScope = rememberCoroutineScope()

            Column(
                Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Transparent,
                    indicator = {}

                ) {
                    pages.forEachIndexed { index, page ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch { pagerState.animateScrollToPage(index) }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = page.drawableResId),
                                    contentDescription = stringResource(id = page.titleResId),
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(vertical = 8.dp, horizontal = 8.dp)
                                )
                            },
                            selectedContentColor = MaterialTheme.colorScheme.inversePrimary,
                            unselectedContentColor = MaterialTheme.colorScheme.secondary,
                        )
                    }

                }

                when (viewModel.settingsUiState) {
                    is SettingsUiState.Success -> {
                        user = (viewModel.settingsUiState as SettingsUiState.Success).user

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            userScrollEnabled = false
                        ) { page ->
                            when (pages[page]) {
                                SettingsPage.PHOTO -> {
                                    val viewModel: ContentViewModel = hiltViewModel<ContentViewModel, ContentViewModel.Factory>(key = user?.uid) {
                                            factory -> factory.create(user=user!!, photographer = null)
                                    }
                                    ContentScreen(navigateHome = navigateHome, viewModel = viewModel)
                                }

                                SettingsPage.MARK -> {
                                    val viewModel: MarkViewModel = hiltViewModel<MarkViewModel, MarkViewModel.Factory>(
                                        key = user?.uid
                                    ) { factory ->
                                        factory.create(photographer = null, user = user)
                                    }
                                    MarkScreen(navigateHome = navigateHome, viewModel = viewModel)
                                }

                                SettingsPage.INFO -> {
                                    InfoScreen(navigateHome = navigateHome)
                                }
                            }

                        }
                    }
                    is SettingsUiState.Error -> {}
                    is SettingsUiState.Loading -> {

                    }
                }

            }
        }
    ) { innerPadding ->
        when (viewModel.settingsUiState) {
            is SettingsUiState.Success -> {
                user = (viewModel.settingsUiState as SettingsUiState.Success).user
                SettingsPage(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .fillMaxSize(),
                    user = user,
                    viewModel = viewModel
                )

            }

            is SettingsUiState.Loading -> {
                PagerLoading()
            }

            is SettingsUiState.Error -> {
                val message = (viewModel.settingsUiState as SettingsUiState.Error).error
                PagerError(message)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(modifier: Modifier, user: User, viewModel: SettingsViewModel) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(24.dp))
        CircleNetworkImage(modifier = Modifier.size(82.dp), user.photoUrl)
        Spacer(Modifier.height(4.dp))
        Text(user.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(4.dp))
        Text(user.email, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(4.dp))
        Text(stringResource(R.string.total_View), style = MaterialTheme.typography.titleLarge)
    }
}
