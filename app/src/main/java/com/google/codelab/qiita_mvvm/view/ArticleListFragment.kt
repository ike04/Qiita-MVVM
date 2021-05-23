package com.google.codelab.qiita_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.codelab.qiita_mvvm.databinding.FragmentArticleListBinding
import com.google.codelab.qiita_mvvm.viewModel.ArticleListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ArticleListFragment : Fragment() {
    private lateinit var binding: FragmentArticleListBinding
    private lateinit var viewModel: ArticleListViewModel
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

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
                viewModel.fetchArticles(binding.keywordEditText.text.toString())
            } else {
                Toast.makeText(requireContext(), "検索ワードを入力してください", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.articleRepos.observe(this, { articles ->
            if (articles.isEmpty()) {
                binding.hasArticles = false
            } else {
                binding.hasArticles = true
                groupAdapter.update(articles.map { ArticleListItemFactory(it) })
            }
        })
    }

}
