package com.google.codelab.qiita_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.codelab.qiita_mvvm.R
import com.google.codelab.qiita_mvvm.databinding.FragmentArticleListBinding
import com.google.codelab.qiita_mvvm.model.Article
import com.google.codelab.qiita_mvvm.viewModel.ArticleListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

class ArticleListFragment : Fragment() {
    private lateinit var binding: FragmentArticleListBinding
    private lateinit var viewModel: ArticleListViewModel
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

        val factory: ViewModelProvider.Factory = ViewModelProvider.NewInstanceFactory()
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(ArticleListViewModel::class.java)

        binding.hasArticles = false
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
                if (articles.size < 20) {
                    isMoreLoad = false
                }
                if (articles.isEmpty()) {
                    Toast.makeText(requireContext(), R.string.no_articles, Toast.LENGTH_SHORT)
                        .show()
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
            }

        groupAdapter.setOnItemClickListener(onItemClickListener)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && isMoreLoad) {
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
