package com.google.codelab.qiita_mvvm.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.codelab.qiita_mvvm.ArticleListRepository
import com.google.codelab.qiita_mvvm.model.Article

class ArticleListViewModel: ViewModel() {
    private val repository = ArticleListRepository()
    private val _articleRepos: MutableLiveData<List<Article>> = MutableLiveData()
    val articleRepos: LiveData<List<Article>> = _articleRepos
    var keyword: String?  = null

    @SuppressLint("CheckResult")
    fun fetchArticles(keyword: String, page: Int) {
        repository.fetchArticles(keyword, page)
            .subscribe { articleRepos: List<Article> ->
                _articleRepos.postValue(articleRepos)
            }
    }
}
