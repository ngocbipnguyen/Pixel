package com.bachnn.feature.collection

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
    photo: PixelsPhoto
) {
    val signature = ObjectKey(photo.src.original)
    val context = LocalContext.current

    val requestManager = remember { Glide.with(context) }

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val widthRateDp: Float =
        pxToDp(photo.width).toFloat() / configuration.screenWidthDp.toFloat()
    val height = photo.height / widthRateDp

    val colorCode = Color(android.graphics.Color.parseColor(photo.avgColor))

    Box(
        modifier = Modifier.background(colorCode)
    ) {
        GlideImage(
            model = photo.src.original,
            contentDescription = "",
            Modifier
                .size(screenWidthDp, pxToDp(height.toInt()).dp)
                .fillMaxWidth()
                .clickable {

                },
            contentScale = ContentScale.Fit
        ) {
            it.thumbnail(
                requestManager
                    .asDrawable()
                    .load(photo.src.small)
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
}

fun pxToDp(px: Int): Int {
    val dp = px / Resources.getSystem().displayMetrics.density
    return dp.toInt()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShowPhotoCollection(photos: List<PhotoSrc>) {
    Row {
        Card(
            Modifier
                .weight(1f)
                .height(140.dp)
                .padding(4.dp),
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
        ) {
            GlideImage(
                model = photos[0].medium,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }

        GlideImage(
            model = photos[1].medium,
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
                model = photos[2].medium,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
    }
}