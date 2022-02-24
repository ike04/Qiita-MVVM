package com.google.codelab.qiita_mvvm.repository

import com.google.codelab.qiita_mvvm.model.Article
import io.reactivex.Single

interface ArticleListRepository {
    fun fetchArticles(tag: String, page: Int): Single<List<Article>>
}
