package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.Collection
import com.bachnn.data.model.PhotoSrc
import com.bachnn.feature.collection.view.CircleNetworkImage
import com.bachnn.feature.viewpager.R
import com.bachnn.feature.viewpager.viewmodel.FollowUiState
import com.bachnn.feature.viewpager.viewmodel.FollowViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowScreen(
    viewModel: FollowViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.title_challenge)) },
            scrollBehavior = scrollBehavior
        )
    }) { contentPadding ->
        when (viewModel.followUiState) {
            is FollowUiState.Success -> {
                FollowPage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = contentPadding.calculateTopPadding()),
                    viewModel = viewModel,
                    scrollBehavior = scrollBehavior
                )
            }

            is FollowUiState.Loading -> {

            }

            is FollowUiState.Error -> {

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowPage(
    modifier: Modifier, viewModel: FollowViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {

}


/*
*
* Challenge
*
* */

@Composable
fun ChallengeGroup(
    modifier: Modifier,
    collections: List<Collection>
) {

    val listState = rememberLazyStaggeredGridState()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    Column(modifier = modifier) {
        Text(stringResource(R.string.title_challenge), style = MaterialTheme.typography.titleLarge)
        Text(
            stringResource(R.string.discription_challenge),
            style = MaterialTheme.typography.bodyLarge
        )
        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(1),
            horizontalItemSpacing = 4.dp,
            state = listState,
            modifier = modifier
                .fillMaxSize()
        ) {
            items(collections, key = { it.id }) { it ->
                ItemChallenge(
                    modifier = Modifier
                        .width(screenWidthDp)
                        .height(screenWidthDp),
                    item = it
                )
            }
        }
    }
}

@Composable
fun ItemChallenge(modifier: Modifier, item: Collection) {
    Column {
        GroupPhoto(modifier = modifier, items = item.medias)
        Text(item.title, style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .width(120.dp)
                    .padding(start = 12.dp, end = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text("11 Days", style = MaterialTheme.typography.bodySmall)
            }

            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .width(120.dp)
                    .padding(start = 12.dp, end = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text("$30", style = MaterialTheme.typography.bodySmall)
            }
        }

        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(stringResource(R.string.join), style = MaterialTheme.typography.bodyLarge)
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GroupPhoto(modifier: Modifier, items: List<PhotoSrc>) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)) {
            GlideImage(
                model = items[0].medium,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 4.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(bottom = 4.dp)) {
                GlideImage(
                    model = items[1].medium,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(topEnd = 8.dp)),
                    contentScale = ContentScale.Fit
                )

                GlideImage(
                    model = items[2].medium,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(bottomEnd = 8.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}


/*
*
* LeaderBroad
*
* */

@Composable
fun LeaderBroadGroup(modifier: Modifier, collections: List<Collection>) {
    val leaderBroadState = rememberLazyStaggeredGridState()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.leader_broad),
            style = MaterialTheme.typography.titleLarge
        )

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(1),
            horizontalItemSpacing = 4.dp,
            state = leaderBroadState,
            modifier = modifier
                .fillMaxSize()
        ) {
            items(collections, key = { it.id }) { it ->
                ItemLeaderBroad(
                    modifier = Modifier.fillMaxWidth(),
                    item = it,
                    screenWidthDp
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemLeaderBroad(modifier: Modifier, item: Collection, screenWidthDp: Dp) {
    Box(
        modifier = modifier
            .padding(12.dp)
            .clip(
                RoundedCornerShape(
                    12.dp
                )
            )
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (avatar, row, column) = createRefs()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(row) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                GlideImage(
                    model = item.medias[0].medium,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 4.dp)
                )

                GlideImage(
                    model = item.medias[1].medium,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            CircleNetworkImage(
                modifier = Modifier
                    .size(56.dp)
                    .constrainAs(avatar) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(row.top)
                        bottom.linkTo(row.bottom)
                    },
                item.medias[2].medium
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 8.dp)
                    .constrainAs(column) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(row.bottom)
                    },
                verticalArrangement = Arrangement.Center
            ) {
                Text(item.title, style = MaterialTheme.typography.bodyLarge)
                OutlinedButton(onClick = {

                }) {
                    Text("Follow", style = MaterialTheme.typography.bodyLarge)
                }
            }

        }
    }
}

@Composable
fun MeetupsGroup(modifier: Modifier, collections: List<Collection>) {

    val meetupBroadState = rememberLazyStaggeredGridState()

    Column(modifier = modifier) {
        Text(
            stringResource(R.string.meet_up),
            style = MaterialTheme.typography.titleLarge
        )

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(1),
            horizontalItemSpacing = 4.dp,
            state = meetupBroadState,
            modifier = modifier
                .fillMaxSize()
        ) {
            items(collections, key = { it.id }) { it ->
                ItemMeetup(
                    modifier = Modifier.fillMaxWidth(),
                    item = it,
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemMeetup(modifier: Modifier, item: Collection) {
    Column(modifier = modifier) {
        GlideImage(
            model = item.medias[0].medium,
            contentDescription = "",
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(200.dp)
        )
        Text("", style = MaterialTheme.typography.bodyLarge)
        Text("", style = MaterialTheme.typography.bodyMedium)
        Text("", style = MaterialTheme.typography.bodyMedium)
    }

}
