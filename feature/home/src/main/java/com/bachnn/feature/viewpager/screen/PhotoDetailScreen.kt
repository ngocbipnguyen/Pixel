package com.bachnn.feature.viewpager.screen

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.WindowInsets
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bachnn.data.model.PixelsPhoto
import com.bachnn.feature.collection.shareLink
import com.bachnn.feature.viewpager.R
import com.bachnn.feature.viewpager.viewmodel.PhotoDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.github.panpf.zoomimage.GlideZoomAsyncImage
import com.github.panpf.zoomimage.compose.glide.ExperimentalGlideComposeApi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    pixelsPhoto: PixelsPhoto,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {

    PhotoDetailPage(
        pixelsPhoto = pixelsPhoto,
        onBackPress = onBackPress,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoDetailPage(
    modifier: Modifier = Modifier,
    pixelsPhoto: PixelsPhoto,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    if (activity != null) {
        val window = activity.window

        SideEffect {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    WindowInsetsControllerCompat(
                        window,
                        window.decorView
                    ).hide(WindowInsetsCompat.Type.statusBars())
                }
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    var favoriteState by remember { mutableStateOf(pixelsPhoto.isFavorite) }
    var markState by remember { mutableStateOf(pixelsPhoto.isMark) }

    var enabled by remember { mutableStateOf(true) }
    val animationDuration = 300
    val animatedAlpha: Float by animateFloatAsState(
        if (enabled) 0.0f else 1.0f,
        animationSpec = tween(durationMillis = animationDuration),
        label = "alpha"
    )

    val colorCode = Color(android.graphics.Color.parseColor(pixelsPhoto.avgColor))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorCode)
    ) {
        val signature = ObjectKey(pixelsPhoto.id)
        val context = LocalContext.current
        val requestManager = remember { Glide.with(context) }

        GlideZoomAsyncImage(
            model = pixelsPhoto.src.original,
            contentDescription = "view image",
            modifier = Modifier
                .fillMaxSize(),
            onTap = {
                enabled = !enabled
            }
        ) {
            it
                .thumbnail(
                    requestManager
                        .asDrawable()
                        .load(pixelsPhoto.src.small)
                        .signature(signature)
                )
                .signature(signature)
        }


        if (animatedAlpha > 0f) {
            // top
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .align(Alignment.TopCenter)
                    .graphicsLayer { alpha = animatedAlpha }
                    .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            ) {
                Spacer(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = null)
                    }

                    Text(
                        pixelsPhoto.photographer,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                            .weight(1f)
                    )

                    IconButton(onClick = {
                        shareLink(context, pixelsPhoto.src.original)
                    }) {
                        Icon(Icons.Default.Send, contentDescription = null)
                    }

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .graphicsLayer { alpha = animatedAlpha }
                    .align(Alignment.BottomCenter)
                    .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    IconButton(
                        onClick = {
                            favoriteState = !pixelsPhoto.isFavorite
                            viewModel.updateFavorite(
                                id = pixelsPhoto.id,
                                favorite = !pixelsPhoto.isFavorite
                            )
                            pixelsPhoto.isFavorite = !pixelsPhoto.isFavorite
                        }, modifier = Modifier
                            .padding(
                                top = 4.dp, start = 4.dp, bottom = 4.dp, end = 4.dp
                            )
                            .size(52.dp)
                    ) {
                        if (favoriteState) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "favorite",
                                tint = Color.Red
                            )
                        } else {
                            Icon(Icons.Default.FavoriteBorder, contentDescription = "favorite")
                        }
                    }

                    IconButton(
                        onClick = {
                            markState = !pixelsPhoto.isMark
                            viewModel.updateMark(id = pixelsPhoto.id, isMark = !pixelsPhoto.isMark)
                            pixelsPhoto.isMark = !pixelsPhoto.isMark
                        }, modifier = Modifier
                            .padding(
                                top = 4.dp, start = 4.dp, bottom = 4.dp, end = 4.dp
                            )
                            .size(52.dp)
                    ) {
                        if (markState) {
                            Icon(
                                Icons.Default.Bookmark,
                                contentDescription = "Bookmark",
                                tint = Color.Yellow
                            )
                        } else {
                            Icon(
                                Icons.Default.BookmarkBorder,
                                contentDescription = "BookmarkBorder"
                            )
                        }
                    }

                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Button(
                        onClick = { viewModel.download(context, pixelsPhoto.src.original) },
                        modifier = Modifier
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary
                        ),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            stringResource(R.string.download),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Spacer(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
            }
        }
        // bottom


    }

}