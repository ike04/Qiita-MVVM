package com.google.codelab.qiita_mvvm

import com.google.codelab.qiita_mvvm.ApiClient.retrofit
import com.google.codelab.qiita_mvvm.model.Article
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class ArticleListRepository {
    fun fetchArticles(tag: String) : Single<List<Article>>{
        return retrofit.create(ApiRequest::class.java).fetchArticles(tag)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map it.body()
            }
    }
}
