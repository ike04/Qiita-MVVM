package com.google.codelab.qiita_mvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.codelab.qiita_mvvm.databinding.FragmentArticleWebViewBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.webView.loadUrl(url)
    }
}
