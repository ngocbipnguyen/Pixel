package com.bachnn.data.model

import com.bachnn.core.network.model.MeetUpNet

data class MeetUp(
    val id: String,
    val url: String,
    val title: String,
    val time: String,
    val description: String
)

fun MeetUpNet.asExternalEntityToDataModel() = MeetUp(
    id = id,
    url = url,
    time = time,
    title = title,
    description = description
)
