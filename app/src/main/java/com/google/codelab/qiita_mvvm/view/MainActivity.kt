package com.google.codelab.qiita_mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.codelab.qiita_mvvm.R
import com.google.codelab.qiita_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, ArticleListFragment())
            .addToBackStack(null)
            .commit()

        setContentView(binding.root)
    }
}
