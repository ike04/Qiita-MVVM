package com.google.codelab.qiita_mvvm

import com.google.codelab.qiita_mvvm.model.Article
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequest {
    @GET("tags/{tag_id}/items")
    fun fetchArticles(
        @Path("tag_id") tagId: String
    ): Single<Response<List<Article>>>
}
