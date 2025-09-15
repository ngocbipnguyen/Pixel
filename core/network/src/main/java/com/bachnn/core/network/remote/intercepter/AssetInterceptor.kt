package com.bachnn.core.network.remote.intercepter

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class AssetInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val assetPath = request.url.encodedPath.substring(1)
        val us = context.assets.open(assetPath)
        val size = us.available()
        val buffer = ByteArray(size)
        us.read(buffer)
        us.close()
        val json = String(buffer, Charsets.UTF_8)
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("ok")
            .body(ResponseBody.create("application/json".toMediaType(), json))
            .build()
    }
}