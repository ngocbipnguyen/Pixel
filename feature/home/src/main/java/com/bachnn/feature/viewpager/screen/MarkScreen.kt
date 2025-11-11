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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun MarkScreen(navigateHome: (Int, Any) -> Unit) {
    MarkPage(
        modifier = Modifier
            .padding(4.dp),
        navigateHome
    )
}

@Composable
fun MarkPage(modifier: Modifier, navigateHome: (Int, Any) -> Unit) {
    Column(modifier = modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .width(150.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ItemMark(
                modifier = Modifier.weight(1f).align(alignment = Alignment.CenterVertically),
                url = "https://images.pexels.com/photos/33162053/pexels-photo-33162053.jpeg?auto=compress&cs=tinysrgb&h=350",
                name = "Favorite"
            ) {
                navigateHome(11, "Favorite")
            }
            ItemMark(
                modifier = Modifier.weight(1f).align(alignment = Alignment.CenterVertically),
                url = "https://images.pexels.com/photos/33123695/pexels-photo-33123695.jpeg?auto=compress&cs=tinysrgb&h=350",
                name = "Download"
            ) {
                navigateHome(12, "Download")
            }
        }

        Spacer(Modifier.weight(1f))

    }
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
                .clickable{
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