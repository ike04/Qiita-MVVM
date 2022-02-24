package com.google.codelab.qiita_mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.codelab.qiita_mvvm.databinding.FragmentArticleWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleWebViewFragment : Fragment() {
    private lateinit var binding: FragmentArticleWebViewBinding
    private val url: String
        get() = checkNotNull(arguments?.getString(URL))

    companion object {
        private const val URL = "url"
        fun newInstance(
            url: String
        ): ArticleWebViewFragment {
            return ArticleWebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleWebViewBinding.inflate(layoutInflater)
        binding.webView.loadUrl(url)

        return binding.root
    }
}
