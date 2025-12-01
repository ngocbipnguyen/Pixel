package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.Photographer
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.collection.INSPIRED_ACTION
import com.bachnn.feature.collection.PHOTO_DETAIL
import com.bachnn.feature.collection.ShowImage
import com.bachnn.feature.collection.ShowImageThumbnail
import com.bachnn.feature.collection.pxToDp
import com.bachnn.feature.collection.screen.PagerError
import com.bachnn.feature.collection.screen.PagerLoading
import com.bachnn.feature.viewpager.R
import com.bachnn.feature.viewpager.viewmodel.ContentUiState
import com.bachnn.feature.viewpager.viewmodel.ContentViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun ContentScreen(photographer: Photographer? = null, modifier: Modifier = Modifier, viewModel: ContentViewModel = hiltViewModel(), navigateHome: (Int, Any) -> Unit) {
    if (photographer != null) {
        when (viewModel.contentUiState) {
            is ContentUiState.Success -> {
                val photos = (viewModel.contentUiState as ContentUiState.Success).allPhotos
                if (photos.isNotEmpty()) {
                    ContentListPage(
                        pixelPhotos = photos,
                        modifier = modifier.fillMaxSize().padding(4.dp),
                        navigateHome = navigateHome
                    )
                } else {
                    ContentPage(modifier.fillMaxSize(), navigateHome)
                }

            }
            is ContentUiState.Loading -> {
                PagerLoading()
            }
            is ContentUiState.Error -> {
                val message = (viewModel.contentUiState as ContentUiState.Error).error
                PagerError(message)
            }
        }
    } else {
        ContentPage(modifier.fillMaxSize(), navigateHome)

    }
}

@Composable
fun ContentPage(modifier: Modifier = Modifier, navigateHome: (Int, Any) -> Unit) {
    Column(
        modifier = modifier.padding(start = 4.dp, end = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            stringResource(R.string.title_content),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            stringResource(R.string.description_content),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {navigateHome(INSPIRED_ACTION, "Get Inspired")},
            modifier = Modifier.width(200.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary
            )
        ) {
            Text(stringResource(R.string.inspired_btn), style = MaterialTheme.typography.titleLarge)
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ContentListPage(pixelPhotos: List<PixelsPhoto>, modifier: Modifier = Modifier, navigateHome: (Int, Any) -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(pixelPhotos) { photo ->
//                GlideImage(
//                    model = photo.src.original,
//                    contentDescription = "",
//                    Modifier
//                        .fillMaxWidth()
//                        .clickable {
//                            // todo
////                            onClickPhoto(photo)
//                        },
//                    contentScale = ContentScale.Fit
//                )
                Box(modifier = Modifier.clip(MaterialTheme.shapes.small)) {
                    ShowImageThumbnail(photo) {
                        navigateHome(PHOTO_DETAIL, photo)
                    }
                }
            }
        },
        modifier = modifier
    )
}



@Preview(showBackground = true)
@Composable
fun ContentPreview() {
//    ContentScreen(navigateHome =  { action, data ->
//
//    })
}