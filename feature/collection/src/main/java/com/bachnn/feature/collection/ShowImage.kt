package com.bachnn.feature.collection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bachnn.data.model.PhotoSrc
import com.bachnn.data.model.PixelsPhoto
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShowImage(
    photo: PhotoSrc
) {
    val signature = ObjectKey(photo.original)
    val context = LocalContext.current

    val requestManager = remember { Glide.with(context) }

    GlideImage(
        model = photo.original,
        contentDescription = "",
        Modifier
            .size(42.dp)
            .fillMaxWidth()
            .clickable {

            },
        contentScale = ContentScale.Fit
    ) {
        it.thumbnail(
            requestManager
                .asDrawable()
                .load(photo.small)
                .signature(signature)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
            .signature(signature)
            .dontAnimate()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .onlyRetrieveFromCache(false)
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShowPhotoCollection(photos: List<PixelsPhoto>) {
    Row {
        Card(
            Modifier
                .weight(1f)
                .height(140.dp)
                .padding(4.dp),
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
        ) {
            GlideImage(
                model = photos[0].src.medium,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }

        GlideImage(
            model = photos[1].src.medium,
            contentDescription = "",
            Modifier
                .weight(1f)
                .height(140.dp)
                .padding(4.dp),
            contentScale = ContentScale.Crop,
        )

        Card(
            Modifier
                .weight(1f)
                .height(140.dp)
                .padding(4.dp),
            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
        ) {
            GlideImage(
                model = photos[2].src.medium,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
    }
}