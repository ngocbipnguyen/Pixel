package com.bachnn.feature.collection.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.PhotoSrc
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.collection.R
import com.bachnn.feature.collection.ShowImage
import com.bachnn.feature.collection.view.CircleNetworkImage
import com.bachnn.feature.collection.viewmodel.PhotoViewModel
import com.bachnn.feature.collection.viewmodel.PixelUiState
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(
    onClickPhoto: (PixelsPhoto) -> Unit,
    onClickPhotographer: (Long) -> Unit,
    viewModel: PhotoViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = Modifier.fillMaxWidth(), topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Pixel", style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(onClick = { /* setting */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }) { contentPadding ->
        when (viewModel.pixelUiState) {
            is PixelUiState.Success -> {
                val photos = (viewModel.pixelUiState as PixelUiState.Success).pixels
                PhotoPage(
                    modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                    viewModel,
                    photos,
                    scrollBehavior,
                    onClickPhoto,
                    onClickPhotographer
                )
            }

            is PixelUiState.Loading -> {
                PagerLoading()
            }

            is PixelUiState.Error -> {
                val message = (viewModel.pixelUiState as PixelUiState.Error).error
                PagerError(message)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoPage(
    modifier: Modifier,
    viewModel: PhotoViewModel,
    photos: List<PixelsPhoto>,
    scrollBehavior: TopAppBarScrollBehavior,
    onClickPhoto: (PixelsPhoto) -> Unit,
    onClickPhotographer: (Long) -> Unit
) {
    val listState = rememberLazyStaggeredGridState()

    val context = LocalContext.current
    val requestManager = remember { Glide.with(context) }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(1),
        verticalItemSpacing = 0.dp,
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        items(photos, key = { it.id }) { it ->

            ItemPhoto(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp), it,
                viewModel,
                onClickPhoto,
                onClickPhotographer
            )
        }
    }
    LaunchedEffect(listState, photos) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }.collect { visibleItems ->
            if (visibleItems.isNotEmpty()) {
                val lastVisibleItemsIndex = visibleItems.last().index
                val totalItemCount = listState.layoutInfo.totalItemsCount

                if (lastVisibleItemsIndex >= totalItemCount - 6) {
//                        viewModel.loadMorePhotos()
                }
            }
        }

        photos.take(20).forEach { url ->
            requestManager.load(url).preload()
        }
    }
}

@Composable
fun ItemPhoto(
    modifier: Modifier,
    photo: PixelsPhoto,
    viewModel: PhotoViewModel,
    onClickPhoto: (PixelsPhoto) -> Unit,
    onClickPhotographer: (Long) -> Unit
) {

    var followState by remember { mutableStateOf(photo.isFollow) }
    var favoriteState by remember { mutableStateOf(photo.isFavorite) }
    val context = LocalContext.current

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleNetworkImage(
                modifier = Modifier
                    .padding(
                        start = 8.dp, bottom = 8.dp, end = 8.dp
                    )
                    .size(42.dp)
                    .clickable {
                        onClickPhotographer(photo.photographerId)
                    },
                photo.src.medium
            )
            Text(
                photo.photographer,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        start = 8.dp, bottom = 8.dp, end = 8.dp
                    )
                    .clickable {
                        onClickPhotographer(photo.photographerId)
                    },
                style = MaterialTheme.typography.titleLarge
            )


            OutlinedButton(
                onClick = {
                    followState = !photo.isFollow
                    viewModel.updateFollow(id = photo.id, follow = !photo.isFollow)
                    photo.isFollow = !photo.isFollow
                }, modifier = Modifier.padding(
                    start = 8.dp, bottom = 8.dp, end = 8.dp
                ),
                shape = MaterialTheme.shapes.small
            ) {
                if (followState) {
                    Text(stringResource(R.string.unfollow))
                } else {
                    Text(stringResource(R.string.follow))
                }
            }

        }

        ShowImage(photo) { it ->
            onClickPhoto(it)
        }

        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    favoriteState = !photo.isFavorite
                    viewModel.updateFavorite(id = photo.id, favorite = !photo.isFavorite)
                    photo.isFavorite = !photo.isFavorite
                }, modifier = Modifier
                    .padding(
                        top = 4.dp, start = 4.dp, bottom = 4.dp, end = 4.dp
                    )
                    .size(56.dp)
            ) {
                if (favoriteState) {
                    Icon(Icons.Default.Favorite, contentDescription = "favorite", tint = Color.Red)
                } else {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "favorite")
                }
            }

            IconButton(
                onClick = {
                    //todo share image.
                    shareLink(context, photo.src.original)

                }, modifier = Modifier
                    .padding(
                        top = 4.dp, bottom = 4.dp
                    )
                    .size(56.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = "send")
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Button(
                onClick = {
                    //todo download
                    viewModel.download(context, photo.src.original)
                },
                modifier = Modifier
                    .padding(
                        top = 8.dp, start = 8.dp, bottom = 8.dp, end = 8.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary
                ),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    stringResource(R.string.download),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

        }

    }
}


fun shareLink(context: Context, url: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share link via")
    context.startActivity(shareIntent)
}
