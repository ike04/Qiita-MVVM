package com.google.codelab.qiita_mvvm.repository

import com.google.codelab.qiita_mvvm.ApiClient.retrofit
import com.google.codelab.qiita_mvvm.ApiRequest
import com.google.codelab.qiita_mvvm.model.Article
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleListRepositoryImpl @Inject constructor(): ArticleListRepository {
    override fun fetchArticles(tag: String, page: Int): Single<List<Article>> {
        return retrofit.create(ApiRequest::class.java).fetchArticles(tag, page, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.body()
            }
    }
}
