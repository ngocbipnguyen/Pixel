package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.Collection
import com.bachnn.data.model.MeetUp
import com.bachnn.data.model.PhotoSrc
import com.bachnn.data.model.Photographer
import com.bachnn.feature.collection.CHALLENGE_ACTION
import com.bachnn.feature.collection.COLLECTION_DETAIL
import com.bachnn.feature.collection.FOLLOW_ACTION
import com.bachnn.feature.collection.MEETUP
import com.bachnn.feature.collection.PHOTOGRAPHER
import com.bachnn.feature.collection.screen.PagerError
import com.bachnn.feature.collection.screen.PagerLoading
import com.bachnn.feature.collection.view.CircleNetworkImage
import com.bachnn.feature.collection.viewmodel.PixelUiState
import com.bachnn.feature.viewpager.R
import com.bachnn.feature.viewpager.viewmodel.FollowUiState
import com.bachnn.feature.viewpager.viewmodel.FollowViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowScreen(
    viewModel: FollowViewModel = hiltViewModel(), navigateHome: (Int, Any) -> Unit
) {

    Scaffold { contentPadding ->
        when (viewModel.followUiState) {
            is FollowUiState.Success -> {
                FollowPage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = contentPadding.calculateTopPadding()),
                    viewModel = viewModel,
                    navigateHome
                )
            }

            is FollowUiState.Loading -> {
                PagerLoading()
            }

            is FollowUiState.Error -> {
                val message = (viewModel.followUiState as FollowUiState.Error).error
                PagerError(message)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowPage(
    modifier: Modifier, viewModel: FollowViewModel,
    navigateHome: (Int, Any) -> Unit
) {
    val uiState = viewModel.followUiState as FollowUiState.Success
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        ChallengeGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp), uiState.collections,
            navigateHome
        )
        LeaderBroadGroup(
            modifier = Modifier.width(screenWidthDp), uiState.photographers,
            navigateHome
        )
        MeetupsGroup(
            modifier = Modifier
                .width(screenWidthDp),
            uiState.meetup,
            navigateHome
        )
    }
}


/*
*
* Challenge
*
* */

@Composable
fun ChallengeGroup(
    modifier: Modifier, collections: List<Collection>,
    navigateHome: (Int, Any) -> Unit
) {

    val listState = rememberLazyStaggeredGridState()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    Column(modifier = modifier) {
        Text(
            stringResource(R.string.title_challenge),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            stringResource(R.string.discription_challenge),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(1),
            state = listState,
            modifier = modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(top = 12.dp, bottom = 16.dp)
        ) {
            items(collections, key = { it.id }) { it ->
                if (it.medias.isNotEmpty()) {
                    ItemChallenge(
                        modifier = Modifier.fillMaxWidth(), item = it,
                        navigateHome
                    )
                }
            }
        }
    }
}

@Composable
fun ItemChallenge(modifier: Modifier, item: Collection, navigateHome: (Int, Any) -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    Column(modifier = modifier.width(screenWidthDp)) {
        GroupPhoto(
            modifier = Modifier
                .width(screenWidthDp * 0.95f)
                .align(alignment = Alignment.CenterHorizontally)
                .height(260.dp)
                .clickable {
                    navigateHome(COLLECTION_DETAIL, item)
                }
            , items = item.medias
        )
        Text(
            item.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .width(screenWidthDp * 0.95f)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 12.dp, bottom = 12.dp),
        ) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .width(160.dp)
                    .padding(end = 12.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text("11 Days", style = MaterialTheme.typography.titleMedium)
            }

            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .width(160.dp)
                    .padding(end = 12.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text("$30", style = MaterialTheme.typography.titleMedium)
            }
        }


        Button(
            onClick = {
                navigateHome(CHALLENGE_ACTION, "Join Challenge")
            },
            modifier = Modifier
                .width(screenWidthDp * 0.95f)
                .align(alignment = Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary
            )
        ) {
            Text(
                stringResource(R.string.join), style = MaterialTheme.typography.titleLarge
            )
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GroupPhoto(modifier: Modifier, items: List<PhotoSrc>) {
    Box(modifier = modifier) {
        Row(modifier = Modifier) {
            GlideImage(
                model = items[0].medium,
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                GlideImage(
                    model = items[1].medium,
                    contentDescription = "",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp, bottom = 4.dp)
                        .clip(RoundedCornerShape(topEnd = 8.dp)),
                    contentScale = ContentScale.FillBounds
                )

                GlideImage(
                    model = items[2].medium,
                    contentDescription = "",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(bottomEnd = 8.dp)),
                    contentScale = ContentScale.FillBounds
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
fun LeaderBroadGroup(
    modifier: Modifier,
    collections: List<Photographer>,
    navigateHome: (Int, Any) -> Unit
) {
    val leaderBroadState = rememberLazyStaggeredGridState()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    Column(modifier = modifier.padding(top = 12.dp)) {
        Text(
            stringResource(R.string.leader_broad),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)

        )

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(1),
            state = leaderBroadState,
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            items(collections, key = { it.id }) { it ->
                ItemLeaderBroadCircle(
                    modifier = Modifier.width(screenWidthDp), item = it, screenWidthDp * 0.95f, navigateHome
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemLeaderBroadCircle(
    modifier: Modifier,
    item: Photographer,
    screenWidthDp: Dp,
    navigateHome: (Int, Any) -> Unit
) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(8.dp)), colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .width(screenWidthDp)
                    .height(160.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .clickable {
                        navigateHome(PHOTOGRAPHER, item.id)
                    }
            ) {
                GlideImage(
                    model = item.photos[0].medium,
                    contentDescription = "",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight() // Fill the height provided by the Row
                        .padding(end = 4.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 8.dp, topStart = 8.dp
                            )
                        ), // Add clipping for better UI
                    contentScale = ContentScale.Crop
                )
                GlideImage(
                    model = item.photos[1].medium,
                    contentDescription = "",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight() // Fill the height provided by the Row
                        .clip(
                            RoundedCornerShape(
                                topEnd = 8.dp, bottomEnd = 8.dp
                            )
                        )
                        .clickable {
                            navigateHome(PHOTOGRAPHER, item.id)
                        }, // Add clipping for better UI
                    contentScale = ContentScale.Crop,
                )
            }

            CircleNetworkImage(
                modifier = Modifier
                    .size(66.dp)
                    .offset(y = (-33).dp)
                    .border(3.dp, Color.White, CircleShape), item.url
            )
            Text(
                item.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.offset(y = (-20).dp)
            )
            OutlinedButton(
                onClick = {
                    navigateHome(FOLLOW_ACTION, "Follow")
                },
                modifier = Modifier
                    .offset(y = (-12).dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    "Follow",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MeetupsGroup(modifier: Modifier, collections: List<MeetUp>, navigateHome: (Int, Any) -> Unit) {

    val meetupBroadState = rememberLazyStaggeredGridState()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.meet_up),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(1),
            state = meetupBroadState,
            modifier = modifier
                .fillMaxSize()
                .height(320.dp)
        ) {
            items(collections, key = { it.id }) { it ->
                ItemMeetup(
                    modifier = Modifier.fillMaxWidth(), item = it, screenWidthDp,navigateHome
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemMeetup(
    modifier: Modifier,
    item: MeetUp,
    screenWidthDp: Dp,
    navigateHome: (Int, Any) -> Unit
) {
    Column(
        modifier = modifier
            .width(screenWidthDp)
            .padding(start = 4.dp, end = 4.dp).clickable{
                navigateHome(MEETUP,item)
            }
    ) {
        GlideImage(
            model = item.url,
            contentDescription = "",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .width(screenWidthDp * 0.95f)
                .height(200.dp)
                .align(alignment = Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            item.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Text(
            item.time,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Text(
            item.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            softWrap = true,
        )
    }

}
