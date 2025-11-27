package com.bachnn.feature.viewpager.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.collection.DOWNLOAD_ACTION
import com.bachnn.feature.collection.FAVORITE_ACTION
import com.bachnn.feature.collection.screen.PagerError
import com.bachnn.feature.collection.screen.PagerLoading
import com.bachnn.feature.viewpager.viewmodel.MarkUiState
import com.bachnn.feature.viewpager.viewmodel.MarkViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun MarkScreen(viewModel: MarkViewModel = hiltViewModel(), navigateHome: (Int, Any) -> Unit) {

    when(viewModel.markUiState) {
        is MarkUiState.Success -> {
            val pixels = (viewModel.markUiState as MarkUiState.Success).allPhotos
            MarkPage(
                modifier = Modifier
                    .padding(4.dp),
                pixels,
                navigateHome
            )
        }
        is MarkUiState.Loading -> {
            PagerLoading()
        }
        is MarkUiState.Error -> {
            val message = (viewModel.markUiState as MarkUiState.Error).error
            PagerError(message)
        }
    }
}

@Composable
fun MarkPage(modifier: Modifier, pixels: List<PixelsPhoto>, navigateHome: (Int, Any) -> Unit) {
    Column {

        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pixels) { pixel ->
                ItemMark(
                    modifier = Modifier,
                    url = pixel.src.medium,
                    name = pixel.photographer
                ) {

                }
            }


        }
        Spacer(Modifier.weight(1f))

    }

//    Column(modifier = modifier) {
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .width(150.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            ItemMark(
//                modifier = Modifier
//                    .weight(1f)
//                    .align(alignment = Alignment.CenterVertically),
//                url = "https://images.pexels.com/photos/33162053/pexels-photo-33162053.jpeg?auto=compress&cs=tinysrgb&h=350",
//                name = "Favorite"
//            ) {
//                navigateHome(FAVORITE_ACTION, "Favorite")
//            }
//            ItemMark(
//                modifier = Modifier
//                    .weight(1f)
//                    .align(alignment = Alignment.CenterVertically),
//                url = "https://images.pexels.com/photos/33123695/pexels-photo-33123695.jpeg?auto=compress&cs=tinysrgb&h=350",
//                name = "Download"
//            ) {
//                navigateHome(DOWNLOAD_ACTION, "Download")
//            }
//        }
//
//        Spacer(Modifier.weight(1f))
//
//    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable()
fun ItemMark(modifier: Modifier, url: String, name: String, navigateHome: () -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            model = url,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(6.dp)
                .width(150.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navigateHome
                }
        )
        Text(name, style = MaterialTheme.typography.titleMedium)
    }
}


@Preview(showBackground = true)
@Composable
fun MarkPreview() {
    MarkScreen(navigateHome = { action, data ->

    })
}