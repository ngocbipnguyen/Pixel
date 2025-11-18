package com.bachnn.feature.collection.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bachnn.data.model.Collection
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.feature.collection.COLLECTION_DETAIL
import com.bachnn.feature.collection.viewmodel.CollectionUiState
import com.bachnn.feature.collection.viewmodel.CollectionViewModel
import com.bachnn.feature.collection.R
import com.bachnn.feature.collection.ShowPhotoCollection

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = hiltViewModel(),
    navigateHome: (Int, Any) -> Unit
) {
    Scaffold(
        topBar = { CollectionTopBar(Modifier) }
    ) { contentPadding ->
        when (viewModel.collectionUiState) {
            is CollectionUiState.Success -> {
                CollectionPagerScreen(
                    Modifier.padding(top = contentPadding.calculateTopPadding()),
                    (viewModel.collectionUiState as CollectionUiState.Success).collection,
                    navigateHome
                )
            }

            is CollectionUiState.Loading -> {
                PagerLoading()
            }

            is CollectionUiState.Error -> {
                PagerError("")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionTopBar(modifier: Modifier) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.collection)) }
    )
}


@Composable
fun CollectionPagerScreen(
    modifier: Modifier,
    collections: List<Collection>,
    navigateHome: (Int, Any) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(1),
        modifier = modifier.fillMaxSize(),
        verticalItemSpacing = 0.dp,
        content = {
            items(collections, key = { it.id }) { it ->
                if (it.medias.isNotEmpty()) {
                    CollectionItem(it, Modifier, navigateHome)
                }
            }
        }
    )
}

@Composable
fun CollectionItem(
    collection: Collection,
    modifier: Modifier,
    navigateHome: (Int, Any) -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp, bottom = 16.dp)
            .clickable {
                navigateHome(COLLECTION_DETAIL,collection)
            },
    ) {
        Column(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        ) {
            if (!collection.medias.isEmpty()) {
                ShowPhotoCollection(collection.medias)
            }

            Text(
                text = collection.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 4.dp)
            )

            Text(
                text = "${collection.mediaCount.toString()} + Video and Photos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

    }
}

@Composable
fun PagerLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(48.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun PagerError(message: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(message)
    }
}

