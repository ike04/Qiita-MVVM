package com.google.codelab.qiita_mvvm.view

import android.content.Context
import com.google.codelab.qiita_mvvm.R
import com.google.codelab.qiita_mvvm.databinding.CellArticleBinding
import com.google.codelab.qiita_mvvm.model.Article
import com.xwray.groupie.databinding.BindableItem

class ArticleListItemFactory(private val article: Article, val context: Context) : BindableItem<CellArticleBinding>() {
    override fun getLayout(): Int = R.layout.cell_article

    override fun bind(viewBinding: CellArticleBinding, position: Int) {
        viewBinding.titleText.text = article.title
        viewBinding.likesCountText.text = context.getString(R.string.LGTM).plus(article.likeCount.toString())
    }
}
