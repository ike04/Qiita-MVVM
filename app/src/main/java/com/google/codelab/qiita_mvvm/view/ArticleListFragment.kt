package com.google.codelab.qiita_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = groupAdapter

        binding.button.setOnClickListener {
            viewModel.fetchArticles(binding.keywordEditText.text.toString())
        }

        viewModel.articleRepos.observe(this, {
            groupAdapter.update(it.map { ArticleListItemFactory(it) })
        })
    }

}
