package com.google.codelab.qiita_mvvm.viewModel

import androidx.lifecycle.ViewModel
import com.google.codelab.qiita_mvvm.Failure
import com.google.codelab.qiita_mvvm.FailureType
import com.google.codelab.qiita_mvvm.Signal
import com.google.codelab.qiita_mvvm.getMessage
import com.google.codelab.qiita_mvvm.model.Article
import com.google.codelab.qiita_mvvm.repository.ArticleListRepository
import com.google.codelab.qiita_mvvm.repository.ArticleListRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val repository: ArticleListRepository
) : ViewModel() {
    val onClickSearch: PublishSubject<Signal> = PublishSubject.create()
    val fetchArticleList: PublishSubject<List<Article>> = PublishSubject.create()
    val errorStream: PublishSubject<FailureType> = PublishSubject.create()
    var keyword: String? = null

    fun fetchArticles(keyword: String, page: Int) {
        repository.fetchArticles(keyword, page)
            .subscribeBy(
                onSuccess = { articleList ->
                    fetchArticleList.onNext(articleList)
                },
                onError = {
                    val f = Failure(getMessage(it)) {
                        fetchArticles(keyword, page)
                    }
                    errorStream.onNext(f.message)
                }
            )
    }

    fun onSearchArticles() {
        onClickSearch.onNext(Signal)
    }
}
