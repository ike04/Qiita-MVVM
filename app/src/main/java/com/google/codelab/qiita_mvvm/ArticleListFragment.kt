package com.google.codelab.qiita_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.codelab.qiita_mvvm.databinding.FragmentArticleListBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ArticleListFragment : Fragment() {
    private lateinit var binding: FragmentArticleListBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val testData: MutableList<Article> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = groupAdapter
        createTestData()
        groupAdapter.update(testData.map { ArticleListItemFactory(it) })
    }

    private fun createTestData() {
        var i = 0
        while (i <= 10) {

            testData.add(
                Article(
                    title = "Sample$i",
                    likeCount = i
                )
            )
            i += 1
        }
    }
}
