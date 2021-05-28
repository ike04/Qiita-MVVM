package com.google.codelab.qiita_mvvm.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("likes_count")
    val likeCount: Int,
    val title: String,
    @SerializedName("updated_at")
    val updateDate: String,
    val url: String
)
