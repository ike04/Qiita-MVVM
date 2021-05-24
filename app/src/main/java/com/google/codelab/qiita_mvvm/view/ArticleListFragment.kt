package com.google.codelab.qiita_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.codelab.qiita_mvvm.R
import com.google.codelab.qiita_mvvm.databinding.FragmentArticleListBinding
import com.google.codelab.qiita_mvvm.model.Article
import com.google.codelab.qiita_mvvm.viewModel.ArticleListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ArticleListFragment : Fragment() {
    private lateinit var binding: FragmentArticleListBinding
    private lateinit var viewModel: ArticleListViewModel
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val articleList: MutableList<Article> = ArrayList()
    private var isMoreLoad = true
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater)

        viewModel = ArticleListViewModel()
        binding.hasArticles = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = groupAdapter

        binding.button.setOnClickListener {
            if (binding.keywordEditText.text.isNotEmpty()) {
                currentPage = 1
                viewModel.fetchArticles(binding.keywordEditText.text.toString(), currentPage)
            } else {
                Toast.makeText(requireContext(), R.string.no_text, Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.articleRepos.observe(this, { articles ->
            if (articles.size < 20) {
                isMoreLoad = false
            }
            if (articles.isEmpty()) {
                binding.hasArticles = false
            } else {
                binding.hasArticles = true
                articleList.addAll(articles)
                groupAdapter.update(articleList.map {
                    ArticleListItemFactory(
                        it,
                        requireContext()
                    )
                })
            }
        })

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && isMoreLoad) {
                    currentPage += 1
                    viewModel.fetchArticles(binding.keywordEditText.text.toString(), currentPage)
                }
            }
        })
    }

}
