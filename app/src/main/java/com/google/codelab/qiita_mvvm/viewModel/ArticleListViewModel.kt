package com.google.codelab.qiita_mvvm.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.google.codelab.qiita_mvvm.Signal
import com.google.codelab.qiita_mvvm.model.Article
import com.google.codelab.qiita_mvvm.repository.ArticleListRepository
import io.reactivex.subjects.PublishSubject

class ArticleListViewModel : ViewModel() {
    private val repository = ArticleListRepository()
    val onClickSearch: PublishSubject<Signal> = PublishSubject.create()
    val fetchArticleList: PublishSubject<List<Article>> = PublishSubject.create()
    var keyword: String? = null

    @SuppressLint("CheckResult")
    fun fetchArticles(keyword: String, page: Int) {
        repository.fetchArticles(keyword, page)
            .onErrorReturnItem(ArrayList())
            .subscribe { articleList ->
                fetchArticleList.onNext(articleList)
            }
    }

    fun onSearchArticles() {
        onClickSearch.onNext(Signal)
    }
}
