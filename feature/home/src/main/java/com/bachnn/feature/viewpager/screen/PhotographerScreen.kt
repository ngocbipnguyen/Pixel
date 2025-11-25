package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.data.model.SocialMedia
import com.bachnn.feature.collection.SETTINGS
import com.bachnn.feature.collection.screen.PagerError
import com.bachnn.feature.collection.screen.PagerLoading
import com.bachnn.feature.collection.view.CircleNetworkImage
import com.bachnn.feature.viewpager.R
import com.bachnn.feature.viewpager.viewmodel.ContentViewModel
import com.bachnn.feature.viewpager.viewmodel.PhotographerUiState
import com.bachnn.feature.viewpager.viewmodel.PhotographerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotographerScreen(
    photographerId: String,
    viewModel: PhotographerViewModel = hiltViewModel(),
    navigateHome: (Int, Any) -> Unit,
    onBackPress: () -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 450.dp,
        sheetContent = {
            val pages = SettingsPage.entries.toTypedArray()
            val pagerState = rememberPagerState(pageCount = {
                pages.size
            })
            val coroutineScope = rememberCoroutineScope()

            Column(
                Modifier
                    .fillMaxWidth()
                    .height(750.dp)
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
                when (viewModel.photographerUiState) {
                    is PhotographerUiState.Success -> {
                        val photographer =
                            (viewModel.photographerUiState as PhotographerUiState.Success).photographer

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            userScrollEnabled = false
                        ) { page ->
                            when (pages[page]) {
                                SettingsPage.PHOTO -> {
                                    val viewModel: ContentViewModel =
                                        hiltViewModel<ContentViewModel, ContentViewModel.Factory>(
                                            key = photographer.id
                                        ) { factory ->
                                            factory.create(photographer)
                                        }
                                    ContentScreen(
                                        photographer = photographer,
                                        viewModel = viewModel,
                                        navigateHome = navigateHome
                                    )
                                }

                                SettingsPage.MARK -> {
                                    MarkScreen(navigateHome = navigateHome)
                                }

                                SettingsPage.INFO -> {
                                    InfoScreen(navigateHome = navigateHome)
                                }
                            }

                        }
                    }

                    is PhotographerUiState.Loading -> {

                    }

                    is PhotographerUiState.Error -> {

                    }

                }

            }


        }
    ) { contentPadding ->
        when (viewModel.photographerUiState) {
            is PhotographerUiState.Success -> {
                val photographer =
                    (viewModel.photographerUiState as PhotographerUiState.Success).photographer
                Scaffold(
                    modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                    topBar = {
                        TopAppBar(
                            title = { }, actions = {

                                Row(
                                    modifier = Modifier.padding(start = 56.dp, end = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    CircleNetworkImage(
                                        modifier = Modifier.size(36.dp),
                                        url = photographer.url
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        photographer.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(Modifier.fillMaxWidth().weight(1f))

                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(shape = MaterialTheme.shapes.small)
                                        .background(
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.1f
                                            )
                                        )
                                ) {
                                    IconButton(onClick = {

                                    }) {
                                        IconButton(
                                            onClick = {},
                                            shape = MaterialTheme.shapes.small
                                        ) {
                                            Icon(
                                                Icons.Default.MoreHoriz,
                                                contentDescription = "more"
                                            )
                                        }
                                    }

                                }

                                Spacer(Modifier.width(8.dp))

                                Button(
                                    onClick = {},
                                    shape = MaterialTheme.shapes.small,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.1f
                                        ),
                                        contentColor = MaterialTheme.colorScheme.onBackground
                                    )
                                ) {
                                    Text(
                                        stringResource(R.string.follow),
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onBackPress()
                                }) {
                                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "back")
                                }

                            }
                        )
                    }
                ) { contentPadding ->
                    PhotographerPage(
                        modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                        photographer
                    )
                }

            }

            is PhotographerUiState.Loading -> {
                PagerLoading()
            }

            is PhotographerUiState.Error -> {
                val message = (viewModel.photographerUiState as PhotographerUiState.Error).error
                PagerError(message)
            }
        }

    }

}

@Composable
fun PhotographerPage(modifier: Modifier = Modifier, photographer: Photographer) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(24.dp))
        CircleNetworkImage(modifier = Modifier.size(82.dp), photographer.url)
        Spacer(Modifier.height(4.dp))
        Text(photographer.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(4.dp))
        Text(photographer.email, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(4.dp))

        SocialViews(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp),
            photographer = photographer
        )

        Spacer(Modifier.height(4.dp))

        RankView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            photographer = photographer
        )

    }

}


@Composable
fun SocialViews(modifier: Modifier, photographer: Photographer) {
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        item {
            SocialView(
                modifier = Modifier,
                SocialMedia(
                    id = "0",
                    name = "Donate",
                    icon = 0,
                    link = "",
                    enable = false,
                    photographerId = 0
                )
            )
        }

        if (photographer.location != "") {
            item {
                SocialView(
                    socialMedia = SocialMedia(
                        id = "0",
                        name = photographer.location,
                        icon = R.drawable.baseline_location_on_24,
                        link = "",
                        enable = false,
                        photographerId = 0
                    )
                )
            }
        }

        item {
            SocialView(
                socialMedia = SocialMedia(
                    id = "0",
                    name = "Instagram",
                    icon = R.drawable.instagram_32,
                    link = "",
                    enable = false,
                    photographerId = 0
                )
            )
        }

        item {
            SocialView(
                socialMedia = SocialMedia(
                    id = "0",
                    name = "Share",
                    icon = R.drawable.send_32,
                    link = "",
                    enable = false,
                    photographerId = 0
                )
            )
        }

        // todo set later.
        items(photographer.social.size) { index ->
            SocialView(socialMedia = photographer.social[index])
        }

    }
}

@Composable
fun SocialView(modifier: Modifier = Modifier, socialMedia: SocialMedia) {
    OutlinedButton(onClick = {}, modifier = modifier, shape = MaterialTheme.shapes.small) {
        if (socialMedia.icon != 0) {

            Icon(
                painter = painterResource(id = socialMedia.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
            )
        }
        Spacer(Modifier.width(4.dp))
        Text(socialMedia.name, style = MaterialTheme.typography.titleSmall)
    }

}


@Composable
fun RankView(modifier: Modifier, photographer: Photographer) {
    Row(modifier = modifier) {

        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                photographer.totalView.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.total_View), style = MaterialTheme.typography.bodyMedium)
        }

        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                photographer.allTimeRank.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.all_time), style = MaterialTheme.typography.bodyMedium)
        }

        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                photographer.monthRank.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.month_rank), style = MaterialTheme.typography.bodyMedium)
        }
    }
}
