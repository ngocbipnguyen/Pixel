//package com.bachnn.feature.viewpager.view
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import com.bachnn.feature.collection.screen.CollectionScreen
//import com.bachnn.feature.collection.screen.PhotoScreen
//import com.bachnn.feature.viewpager.screen.AddScreen
//import com.bachnn.feature.viewpager.screen.FollowScreen
//import com.bachnn.feature.viewpager.screen.HomeScreen
//import com.bachnn.feature.viewpager.screen.SettingsScreen
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//
//@Composable
//fun MainViewPager(onClick: () -> Unit) {
//    val tabs = listOf(
//        Icons.Default.Home,
//        Icons.Default.FavoriteBorder,
//        Icons.Default.Add,
//        Icons.Default.Search,
//        Icons.Default.Settings
//    )
//    val pagerState = rememberPagerState(pageCount = { tabs.size })
//
//    Column(modifier = Modifier.fillMaxWidth()) {
//        HorizontalPager(state = pagerState) { page ->
//            when (page) {
//                0 -> {
//                    PhotoScreen(
//                        onClickPhoto = {
//
//                        },
//                        onClickPhotographer = {
//
//                        })
//                }
//
//                1 -> {
//                    FollowScreen(onClick = onClick)
//                }
//
//                2 -> {
//                    AddScreen(onClick)
//                }
//
//                3 -> {
//                    CollectionScreen(onClickCollection = {
//
//                    })
//                }
//
//                4 -> {
//                    SettingsScreen(onClick = onClick)
//                }
//            }
//        }
//
//        TabRow(
//            modifier = Modifier.fillMaxWidth(),
//            selectedTabIndex = pagerState.currentPage,
//            indicator = {}
//        ) {
//            tabs.forEachIndexed { index, res ->
//                Tab(
//                    selected = pagerState.currentPage == index,
//                    onClick = {
//                        CoroutineScope(Dispatchers.Main).launch {
//                            pagerState.animateScrollToPage(index)
//                        }
//                    },
//                    icon = { Icon(res, contentDescription = "") }
//                )
//            }
//        }
//
//    }
//
//}