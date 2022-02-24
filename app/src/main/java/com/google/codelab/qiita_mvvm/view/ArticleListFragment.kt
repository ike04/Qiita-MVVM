package com.google.codelab.qiita_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.codelab.qiita_mvvm.R
import com.google.codelab.qiita_mvvm.databinding.FragmentArticleListBinding
import com.google.codelab.qiita_mvvm.model.Article
import com.google.codelab.qiita_mvvm.viewModel.ArticleListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

@AndroidEntryPoint
class ArticleListFragment : Fragment() {
    private lateinit var binding: FragmentArticleListBinding
    private val viewModel: ArticleListViewModel by viewModels()
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val articleList: MutableList<Article> = ArrayList()
    private var isMoreLoad = true
    private var currentPage = 1

    private val onItemClickListener = OnItemClickListener { item, _ ->
        // どのitemがクリックされたかindexを取得
        val index = groupAdapter.getAdapterPosition(item)

        fragmentManager?.beginTransaction()
            ?.replace(R.id.frameLayout, ArticleWebViewFragment.newInstance(articleList[index].url))
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater)

        binding.hasArticles = false
        binding.isLoading = false
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = groupAdapter

        viewModel.onClickSearch
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                articleList.clear()
                if (binding.keywordEditText.text.isNotEmpty()) {
                    binding.isLoading = true
                    viewModel.keyword = binding.keywordEditText.text.toString()
                    currentPage = 1
                    viewModel.keyword?.let { viewModel.fetchArticles(it, currentPage) }
                } else {
                    Toast.makeText(requireContext(), R.string.no_text, Toast.LENGTH_SHORT).show()
                }
            }

        viewModel.fetchArticleList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { articles ->
                isMoreLoad = articles.size == 20

                binding.hasArticles = true
                articleList.addAll(articles)
                groupAdapter.update(articleList.map {
                    ArticleListItemFactory(
                        it,
                        requireContext()
                    )
                })
                binding.isLoading = false

            }

        viewModel.errorStream
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { failure ->
                Snackbar.make(view, failure.message, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.retry) {
                        binding.isLoading = true
                        viewModel.keyword?.let { viewModel.fetchArticles(it, currentPage) }
                    }.show()
                binding.isLoading = false
            }

        groupAdapter.setOnItemClickListener(onItemClickListener)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && isMoreLoad) {
                    binding.isLoading = true
                    currentPage += 1
                    viewModel.keyword?.let { viewModel.fetchArticles(it, currentPage) }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (!articleList.isEmpty()) {
            binding.hasArticles = true
        }
    }
}
