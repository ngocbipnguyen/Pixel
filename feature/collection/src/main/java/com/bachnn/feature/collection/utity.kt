package com.bachnn.feature.collection

import android.content.Context
import android.content.Intent

fun shareLink(context: Context, url: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share link via")
    context.startActivity(shareIntent)
}