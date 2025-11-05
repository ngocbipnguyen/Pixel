package com.bachnn.data.model

data class LeaderCategory(val title: String, var isSelect: Boolean)


fun LeaderCategory.clear() = LeaderCategory(
    title = title,
    isSelect = false
)