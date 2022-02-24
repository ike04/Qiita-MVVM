package com.google.codelab.qiita_mvvm

import com.google.codelab.qiita_mvvm.repository.ArticleListRepository
import com.google.codelab.qiita_mvvm.repository.ArticleListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindArticleListRepository(articleListRepositoryImpl: ArticleListRepositoryImpl): ArticleListRepository
}
