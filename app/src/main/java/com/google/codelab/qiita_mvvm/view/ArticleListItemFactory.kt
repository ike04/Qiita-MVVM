package com.google.codelab.qiita_mvvm

import com.google.codelab.qiita_mvvm.databinding.CellArticleBinding
import com.xwray.groupie.databinding.BindableItem

class ArticleListItemFactory(private val article: Article) : BindableItem<CellArticleBinding>() {
    override fun getLayout(): Int = R.layout.cell_article

    override fun bind(viewBinding: CellArticleBinding, position: Int) {
        viewBinding.titleText.text = article.title
        viewBinding.likesCountText.text = article.likeCount.toString()
    }
}
